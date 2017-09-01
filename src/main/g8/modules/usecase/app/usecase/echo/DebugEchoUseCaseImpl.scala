package usecase.echo

import contract.UseCaseContext
import contract.usecase.echo.DebugEchoUseCase
import logger.LogSupport
import scaldi.{ Injectable, Injector }

import scala.concurrent.{ ExecutionContext, Future }

/**
 * DebugEchoUseCase.
 */
case class DebugEchoUseCaseImpl(implicit inj: Injector) extends DebugEchoUseCase with Injectable with LogSupport {
  override protected def call(arg: In)(implicit ec: ExecutionContext, uc: UseCaseContext): Future[Out] = Future {
    "<html><body><h1>Say hello to <i>akka-http-routing</i> on <i>akka-http</i>!</h1></body></html>"
  }
}
