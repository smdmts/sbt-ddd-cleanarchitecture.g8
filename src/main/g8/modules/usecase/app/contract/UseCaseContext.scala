package contract

import java.util.UUID

import scala.collection.mutable

/**
 * UseCaseContext.
 */
trait UseCaseContext {
  val id = UUID.randomUUID().toString
  val logBuffer: mutable.Map[String, Any] = mutable.Map.empty
}
