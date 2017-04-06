package contract

// Output Port
trait CallBack[Result] {

  def onSuccess(result: Result): Unit

  def onFailure(t: Throwable): Unit
}
