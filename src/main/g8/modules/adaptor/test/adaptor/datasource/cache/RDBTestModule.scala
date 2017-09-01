package adaptor.datasource.cache

import java.io.File

import com.typesafe.config.ConfigFactory
import conf.{ AppConf, DBConf }
import scaldi.Module

import scala.concurrent.ExecutionContext

/**
 * RDBTestModule.
 */
class RDBTestModule(implicit ec: ExecutionContext) extends Module {
  implicit val config = ConfigFactory.load(ConfigFactory.parseFileAnySyntax(new File("conf/application.conf")))
  binding toProvider new AppConf
  binding toProvider new DBConf
}

