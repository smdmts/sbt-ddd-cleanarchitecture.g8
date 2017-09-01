package adaptor.http

import adaptor.http.controller.echo._
import adaptor.http.presenter.echo._
import scaldi.Module
import scalikejdbc.DBSession

import scala.concurrent.ExecutionContext

/**
  * HttpModules.
  */
class HttpModules(implicit ec: ExecutionContext) extends Module {
  binding toProvider new HttpService
  binding toProvider new EchoController
  binding toProvider new HealthCheckPresenter
  binding toProvider new DebugEchoPresenter
}
