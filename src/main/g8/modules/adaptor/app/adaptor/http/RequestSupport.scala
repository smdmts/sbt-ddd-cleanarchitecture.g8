package adaptor.http

import akka.http.scaladsl.model.headers.{`User-Agent`, Cookie, HttpCookiePair, Referer}
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives._
import contract.UseCaseContext
import logger.LogSupport
import scaldi.{Injectable, Injector}

import scala.language.postfixOps

/**
  * RequestSupport.
  */
trait RequestSupport
    extends HeaderDirectives
    with DebuggingDirectives
    with LogSupport
    with Injectable {

  implicit val inj: Injector

  def requestInfoContext(parameters: Map[String, String]): Directive1[RequestInfoContext] = {
    Directives.extract { ctx =>
      val requestTime = System.currentTimeMillis
      val ipAddr      = RequestSupport.getIpAddr(ctx)
      val userAgent   = RequestSupport.getUserAgent(ctx)
      val cookie      = RequestSupport.getCookie(ctx)
      val referer     = RequestSupport.getReferer(ctx)
      val uri         = RequestSupport.getURI(ctx)
      if (ipAddr.isEmpty | userAgent.isEmpty) {
        RequestInfoContext(requestTime, "127.0.0.1", userAgent, cookie, referer, uri, parameters)
      } else {
        RequestInfoContext(requestTime, ipAddr.head, userAgent, cookie, referer, uri, parameters)
      }
    }
  }

  val bodyParameterMultiMap: Directive1[String] = BasicDirectives.extract { ctx =>
    throw new UnsupportedOperationException
  }

  sealed abstract class Method(val method: String) extends Directives {

    def apply(pm: PathMatcher[_]): Directive1[RequestInfoContext] = {
      path(pm).tflatMap { _ =>
        pathEndOrSingleSlash
      }.tflatMap { _ =>
        methodRoute
      }.tflatMap { _ =>
        logDuration
      }.tflatMap { _ =>
        parameterMap
      }.tflatMap { parameterMap =>
        requestInfoContext(parameterMap._1)
      }
    }

    val logDuration = extractRequestContext.flatMap { ctx =>
      val start = System.currentTimeMillis()
      // handling rejections here so that we get proper status codes
      mapResponse { resp =>
        val duration = System.currentTimeMillis() - start
        val message = Map("status" -> resp.status.intValue().toString,
                          "method"  -> ctx.request.method.name,
                          "uri"     -> ctx.request.uri.toString,
                          "latency" -> duration.toString)
        resp
      } & handleRejections(RejectionHandler.default)
    }

    def methodRoute: Directive0 = {
      this match {
        case GET    => MethodDirectives.get
        case POST   => MethodDirectives.post
        case PUT    => MethodDirectives.put
        case DELETE => MethodDirectives.delete
        case PATCH  => MethodDirectives.patch
      }
    }

    def internal(pm: PathMatcher[_]): Directive1[RequestInfoContext] = {
      path(pm).tflatMap { _ =>
        pathEndOrSingleSlash
      }.tflatMap { _ =>
        methodRoute
      }.tflatMap { _ =>
        Directives.extract { ctx =>
          val requestTime = System.currentTimeMillis
          val userAgent   = RequestSupport.getUserAgent(ctx)
          val cookie      = RequestSupport.getCookie(ctx)
          val referer     = RequestSupport.getReferer(ctx)
          val uri         = RequestSupport.getURI(ctx)
          RequestInfoContext(requestTime, "127.0.0.1", userAgent, cookie, referer, uri)
        }
      }
    }

  }

  case object GET extends Method("GET")

  case object POST extends Method("POST")

  case object PUT extends Method("PUT")

  case object DELETE extends Method("DELETE")

  case object PATCH extends Method("PATCH")

}

object RequestSupport {

  val withCookie: Directive1[Map[String, String]] = Directives.extract { ctx =>
    getCookie(ctx)
  }

  def getIpAddr(requestContext: RequestContext): Seq[String] = {
    val ipHeaders = Seq(
      "Remote-Address",
      "HttpIp",
      "X-Forwarded-For",
      "X-Real-IP"
    )
    requestContext.request.headers
      .filter(h => ipHeaders.contains(h.name))
      .flatMap { h =>
        h.value.split(',')
      }
      .headOption
      .toSeq
  }

  def getUserAgent(requestContext: RequestContext): String =
    requestContext.request.headers.collect {
      case `User-Agent`(ua) => ua
    } mkString

  def getReferer(requestContext: RequestContext): String =
    requestContext.request.headers.collect {
      case Referer(r) => r
    } mkString

  def getCookie(requestContext: RequestContext): Map[String, String] = {
    val cookieSeq: Seq[HttpCookiePair] = requestContext.request.headers.collect {
      case Cookie(cookies) => cookies
    }.flatten
    cookieSeq.map { cookie =>
      cookie.name -> cookie.value
    } toMap
  }

  def getURI(requestContext: RequestContext): String =
    requestContext.request.uri.path.toString

}

case class RequestInfoContext(requestTime: Long,
                              ipAddress: String,
                              agent: String,
                              cookies: Map[String, String] = Map(),
                              referer: String,
                              uri: String,
                              parameters: Map[String, String] = Map())
    extends UseCaseContext {
  def mapWithHeaderDataMap(m: Map[String, List[String]]) = m ++ cookies.map { cookie =>
    s"cookie.\${cookie._1}" -> List(cookie._2)
  }
}
