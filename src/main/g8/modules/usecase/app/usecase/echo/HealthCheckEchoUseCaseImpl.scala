package usecase.echo

import contract.UseCaseContext
import contract.usecase.echo.HealthCheckEchoUseCase
import scaldi.Injector

import scala.concurrent.{ ExecutionContext, Future }

/**
 * HealthCheckEchoUseCase.
 */
class HealthCheckEchoUseCaseImpl(implicit inj: Injector) extends HealthCheckEchoUseCase {
  override protected def call(arg: In)(implicit ec: ExecutionContext, uc: UseCaseContext): Future[Out] = Future {
    "200 OK"
  }
}
