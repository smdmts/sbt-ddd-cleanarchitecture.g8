package adaptor.http

import contract.CallBack

import scala.concurrent.{ExecutionContext, Future}

trait Presenter[C <: CallBack[_], R] {

  type UseCaseExecutor = C => Unit

  type Rendered = R

  def response(call: UseCaseExecutor)(implicit ec: ExecutionContext,
                                      uc: RequestInfoContext): Future[Rendered]

}
