package external.akka

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.Config
import scaldi.{Injectable, Injector}

/**
  * AkkaManager.
  */
class AkkaManager(implicit inj: Injector, config: Config) extends Injectable {
  implicit val system       = ActorSystem("am", config)
  implicit val materializer = ActorMaterializer()
  implicit val ec           = system.dispatcher
}
