package adaptor.datasource.aws.dynamodb

import com.typesafe.config.Config
import scaldi.Module

import scala.concurrent.ExecutionContext

/**
  * DynamoDBModules.
  */
class DynamoDBModules(implicit ec: ExecutionContext, config: Config) extends Module {
  binding toProvider new DynamoDBClient destroyWith (_.client.shutdown())
}
