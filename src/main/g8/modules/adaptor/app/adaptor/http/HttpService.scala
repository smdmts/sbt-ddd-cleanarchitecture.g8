package adaptor.http

import adaptor.http.controller.echo._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import scaldi.{Injectable, Injector}

import scala.concurrent.ExecutionContext

/**
  * HttpService.
  */
class HttpService(implicit inj: Injector, ec: ExecutionContext)
    extends CorsSupport
    with Injectable {
  val debugController = inject[EchoController]
  val routes: Route   = debugController.route
}
