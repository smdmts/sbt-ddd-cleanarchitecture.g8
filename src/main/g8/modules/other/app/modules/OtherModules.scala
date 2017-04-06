package modules

import com.typesafe.config.Config
import conf.{ AppConf, AwsConf, DBConf }
import scaldi.Module

import scala.concurrent.ExecutionContext

/**
 * OtherModule.
 */
class OtherModules(implicit ec: ExecutionContext, config: Config) extends Module {
  binding toProvider new AppConf
  binding toProvider new AwsConf
  binding toProvider new DBConf
}
