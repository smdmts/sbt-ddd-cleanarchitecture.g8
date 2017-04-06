package adaptor.datasource.cache

/**
  * CacheFeature.
  */
trait CacheFeature {
  def refresh(): Unit
}
