package adaptor.http.presenter.echo

import adaptor.http.RequestInfoContext
import adaptor.http.Presenter
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import contract.callback.echo.DebugEchoCallBack

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * DebugEchoPresenter.
  */
class DebugEchoPresenter extends Presenter[DebugEchoCallBack, HttpResponse] {

  override def response(call: UseCaseExecutor)(implicit ec: ExecutionContext,
                                               uc: RequestInfoContext): Future[Rendered] = {
    val callback = new CallbackImpl
    call(callback)
    callback.promise.future
  }

  private class CallbackImpl extends DebugEchoCallBack {
    val promise = Promise[HttpResponse]()

    override def onFailure(throwable: Throwable): Unit = {
      promise.failure(throwable)
    }

    override def onSuccess(result: String): Unit = {
      promise.success(HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, result)))
    }
  }
}
