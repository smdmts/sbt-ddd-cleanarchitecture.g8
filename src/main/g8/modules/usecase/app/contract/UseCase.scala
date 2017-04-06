package contract

import scala.concurrent.{ ExecutionContext, Future }

trait UseCase {
  type In

  type Out

  protected def call(arg: In)(implicit ec: ExecutionContext, uc: UseCaseContext): Future[Out]
}
