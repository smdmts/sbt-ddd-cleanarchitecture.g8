package external.akka

import com.typesafe.config.Config
import scaldi.Module

/**
  * AkkaModule.
  */
class AkkaModules(implicit config: Config) extends Module {
  binding toProvider new AkkaManager
}
