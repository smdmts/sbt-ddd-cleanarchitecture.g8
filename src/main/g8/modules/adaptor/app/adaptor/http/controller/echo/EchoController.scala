package adaptor.http.controller.echo

import adaptor.http._
import adaptor.http.presenter.echo._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatcher._
import akka.http.scaladsl.server.Route
import contract.usecase.echo.{DebugEchoUseCase, HealthCheckEchoUseCase}
import scaldi._

import scala.concurrent.ExecutionContext

/**
  * Echo.
  */
class EchoController(implicit val inj: Injector, ec: ExecutionContext)
    extends Injectable
    with RequestSupport {

  val healthCheckPresenter = inject[HealthCheckPresenter]
  val healthCheckUseCase   = inject[HealthCheckEchoUseCase]
  val debugEchoUseCase     = inject[DebugEchoUseCase]
  val debugEchoPresenter   = inject[DebugEchoPresenter]

  /**
    * DebugRoutes.
    */
  val debugRoutes = GET("debug") { implicit context =>
    complete {
      debugEchoPresenter.response(debugEchoUseCase.execute(Unit))
    }
  }

  /**
    * HealthCheckRoutes.
    */
  val healthCheckRoutes = GET("healthcheck") { implicit ctx =>
    complete(healthCheckPresenter.response(healthCheckUseCase.execute(Unit)))
  }

  val route: Route = debugRoutes ~ healthCheckRoutes

}
