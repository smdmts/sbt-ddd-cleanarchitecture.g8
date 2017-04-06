package adaptor.datasource.aws.dynamodb

import awscala._
import awscala.dynamodbv2.DynamoDB
import conf._
import scaldi.{Injectable, Injector}

/**
  * DynamoDBClient.
  */
class DynamoDBClient(implicit inj: Injector) extends Injectable {
  val appConf = inject[AppConf]
  val awsConf = inject[AwsConf]

  implicit val region = Region(awsConf.region)

  /** dynamodb. */
  private[dynamodb] val dynamoDB = {
    val dbOpt = for {
      accessKeyId     <- awsConf.accessKey if !accessKeyId.isEmpty
      secretAccessKey <- awsConf.secretAccessKey if !secretAccessKey.isEmpty
    } yield DynamoDB(accessKeyId, secretAccessKey)
    dbOpt.getOrElse(DynamoDB())
  }

  val client: DynamoDB = {
    dynamoDB.setEndpoint(awsConf.dynamodbEndpoint)
    dynamoDB
  }
}
