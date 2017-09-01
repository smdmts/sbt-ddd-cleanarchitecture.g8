package contract

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }

// Input Port
trait PushPort[Arg, Result] {
  self: UseCase =>

  override final type In = Arg

  override final type Out = Result

  def execute[T <: CallBack[Result]](arg: Arg)(callback: T)(implicit ec: ExecutionContext, uc: UseCaseContext): Unit = {
    call(arg).onComplete {
      case Success(result) =>
        callback.onSuccess(result)
      case Failure(t) =>
        callback.onFailure(t)
    }
  }
}
