
import adaptor.datasource.cache.CacheModules
import adaptor.http.HttpService
import akka.http.scaladsl.Http
import com.typesafe.config.{ Config, ConfigFactory }
import conf.DBConf
import modules.Modules
import scaldi.Injectable

/**
 * BootStrap.
 */
object Bootstrap extends App with Injectable {

  implicit val config = loadConfig()

  // init first akka system.
  val actorManager = Modules.createAkkaSystem()

  implicit val system = actorManager.system
  implicit val ec = actorManager.ec
  implicit val materializer = actorManager.materializer
  implicit val injector = Modules()

  val httpService = inject[HttpService]
  val dbConfig = inject[DBConf]

  // initialize db.
  dbConfig.initialize()

  // refreshable after initialized db.
  CacheModules.refresh()

  Http().bindAndHandle(httpService.routes, "127.0.0.1", 8080)

  def loadConfig(): Config = {
    val conf = System.getProperties
    val base = ConfigFactory.load()
    val addition = Option(conf.getProperty("config.resource")).map { resource =>
      ConfigFactory.parseResources(getClass.getClassLoader, resource)
    } getOrElse {
      ConfigFactory.parseResources(getClass.getClassLoader, "application.conf")
    }
    base.withFallback(addition)
  }

}
