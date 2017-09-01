package conf

import net.ceedubs.ficus.Ficus._
import com.typesafe.config._

/**
 * AppConf.
 */
class AppConf(implicit config: Config) {
  val root = config
  val appMode: String = config.as[String]("application.constitution")
}
