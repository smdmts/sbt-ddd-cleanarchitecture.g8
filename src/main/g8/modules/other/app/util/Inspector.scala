package util

/**
 * Inspector.
 */
object Inspector {

  import scala.runtime.ScalaRunTime._
  def apply[T](t: T) = stringOf(t)

  implicit class ProductMappable(val any: Any) extends AnyVal {
    def toMap: Map[String, Any] = any match {
      case v: Product => Mappable(v).toMap
      case _          => sys.error("error.")
    }
    def toMapWithPrefix(prefix: String): Map[String, Any] = any match {
      case v: Product => Mappable(v).toMapWithPrefix(prefix)
      case _          => sys.error("error.")
    }
  }

  implicit class Mappable(val value: Product) extends AnyVal {
    /**
     * Converts case class to map.
     */
    def toMap: Map[String, Any] = {
      value.getClass.getDeclaredFields.map(_.getName).zip(value.productIterator.toList).toMap
    }

    def toMapWithPrefix(prefix: String) = {
      this.getClass.getDeclaredFields.map(prefix + "." + _.getName).zip(value.productIterator.toList).toMap
    }
  }
}
