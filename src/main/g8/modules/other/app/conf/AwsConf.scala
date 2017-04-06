package conf

import scaldi.{ Injectable, Injector }
import net.ceedubs.ficus.Ficus._

/**
 * AwsConf.
 */
class AwsConf(implicit inj: Injector) extends Injectable {
  val appConf = inject[AppConf]

  val accessKey = appConf.root.getAs[String]("accessKey")
  val secretAccessKey = appConf.root.getAs[String]("secretAccessKey")
  val region: String = appConf.root.as[String]("aws.region")

  // kinesis
  // TODO 名称は仮
  val streamName = appConf.root.getAs[String]("aws.kinesis.stream.name")

  // dynamodb
  val dynamodbEndpoint: String = appConf.root.as[String]("aws.dynamodb.endpoint")

  // s3
  // log
  val s3bucket_log: String = appConf.root.as[String]("aws.s3.bucket.log")

}
