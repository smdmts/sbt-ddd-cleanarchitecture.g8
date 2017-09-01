package adaptor.datasource.aws.dynamodb

import java.io.File

import com.typesafe.config.ConfigFactory
import conf.{ AppConf, AwsConf, DBConf }
import scaldi.Module

import scala.concurrent.ExecutionContext

/**
 * DynamoDbTestModule.
 */
class DynamoDbTestModule(implicit ec: ExecutionContext) extends Module {
  implicit val config = ConfigFactory.load(ConfigFactory.parseFileAnySyntax(new File("conf/application.conf")))
  binding toProvider new AppConf
  binding toProvider new AwsConf
  binding toProvider new DBConf
  binding toProvider new DynamoDBClient
}
