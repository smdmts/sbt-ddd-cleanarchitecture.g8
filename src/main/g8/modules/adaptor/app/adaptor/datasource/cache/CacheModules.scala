package adaptor.datasource.cache

import scaldi.{Injectable, Injector, Module}

/**
  * CacheModules.
  */
class CacheModules extends Module {}

object CacheModules extends Injectable {
  def refresh()(implicit inj: Injector) = {}
}
