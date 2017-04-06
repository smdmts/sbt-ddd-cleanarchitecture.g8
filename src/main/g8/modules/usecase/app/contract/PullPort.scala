package contract

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }

trait PullPort[PullArg, Arg, Result] {
  self: UseCase =>

  override final type In = Arg

  override final type Out = Result

  def execute[T <: CallBack[Result]](callback: T)(implicit ec: ExecutionContext, uc: UseCaseContext): Unit = {
    call(arg(pullArg)).onComplete {
      case Success(result) =>
        callback.onSuccess(result)
      case Failure(t) =>
        callback.onFailure(t)
    }
  }

  protected def arg: PullArg => Arg

  protected def pullArg: PullArg
}
