package adaptor.http.presenter.echo

import adaptor.http.{Presenter, RequestInfoContext}
import contract.callback.echo.HealthCheckEchoCallBack

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * HealthCheckPresenter.
  */
class HealthCheckPresenter extends Presenter[HealthCheckEchoCallBack, String] {

  override def response(call: UseCaseExecutor)(implicit ec: ExecutionContext,
                                               uc: RequestInfoContext): Future[Rendered] = {
    val callback = new CallbackImpl
    call(callback)
    callback.promise.future
  }

  private class CallbackImpl extends HealthCheckEchoCallBack {
    val promise = Promise[String]()

    override def onFailure(throwable: Throwable): Unit = {
      promise.failure(throwable)
    }

    override def onSuccess(result: String): Unit = {
      promise.success(result)
    }
  }
}
