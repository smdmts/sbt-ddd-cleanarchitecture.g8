package modules

import adaptor.datasource.aws.dynamodb.DynamoDBModules
import adaptor.datasource.cache.CacheModules
import adaptor.http.HttpModules
import com.typesafe.config.Config
import external.akka.{AkkaManager, AkkaModules}
import scaldi.{Injectable, Injector}

import scala.concurrent.ExecutionContext

/**
  * Modules.
  */
object Modules extends Injectable {

  def akkaModules()(implicit config: Config): Injector = {
    new AkkaModules
  }

  def createAkkaSystem()(implicit config: Config): AkkaManager = {
    implicit val akkaInjector = akkaModules()
    val manager               = inject[AkkaManager]
    manager
  }

  def apply()(implicit ec: ExecutionContext, config: Config): Injector = {
    val modules = new HttpModules ::
      new OtherModules ::
      new UseCaseModule ::
      new CacheModules ::
      new DynamoDBModules
    staticModulesOpt = Some(modules)
    modules
  }

  private var staticModulesOpt: Option[Injector] = None

  def staticModules = staticModulesOpt.get
}
