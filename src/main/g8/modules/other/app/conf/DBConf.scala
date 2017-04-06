package conf

import com.typesafe.config._
import com.zaxxer.hikari.{ HikariConfig, HikariDataSource }
import scaldi.{ Injectable, Injector }
import net.ceedubs.ficus.Ficus._
import scalikejdbc.{ ConnectionPool, DataSourceConnectionPool }

import scala.collection.JavaConverters._

/**
 * DBConf
 */
class DBConf(implicit inj: Injector) extends Injectable {
  val appConf = inject[AppConf]
  val rdbDefinitions = appConf.root.getConfig("db").root.asScala.map {
    case (name, config) =>
      RDBDefinitions(name, config)
  }

  def rdbDefinition(name: String) = {
    rdbDefinitions.find(_.name == name).get
  }

  def initialize() = {
    rdbDefinitions.foreach { conf =>
      val ds = new HikariDataSource(conf.hikariConfig)
      ConnectionPool.add(Symbol(conf.name), new DataSourceConnectionPool(ds))
    }
  }
}

case class RDBDefinitions private (name: String, config: ConfigValue) {
  private val root = ConfigFactory.empty().withFallback(config)
  val driver = root.as[String]("driver")
  val url = root.as[String]("url")
  val userName = root.as[String]("user")
  val password = root.as[String]("password")
  val connectionPoolName = root.as[String]("connectionPoolName")
  val maximumPoolSize = root.getAs[String]("maximumPoolSize").getOrElse("30")
  val connectionTimeout = root.getAs[String]("connectionTimeout").getOrElse("30")
  val idleTimeout = root.getAs[String]("idleTimeout").getOrElse("600000")
  val maxLifetime = root.getAs[String]("maxLifetime").getOrElse("1800000")
  val connectionInitSqlOpt = root.getAs[String]("connectionInitSql")
  val hikariConfig = {
    val config = new HikariConfig()
    config.setUsername(userName)
    config.setPassword(password)
    config.setDriverClassName(driver)
    config.setJdbcUrl(url)
    config.addDataSourceProperty("poolName", connectionPoolName)
    config.addDataSourceProperty("maximumPoolSize", maximumPoolSize)
    config.addDataSourceProperty("connectionTimeout", connectionTimeout)
    config.addDataSourceProperty("idleTimeout", idleTimeout)
    config.addDataSourceProperty("maxLifetime", maxLifetime)
    config.addDataSourceProperty("cachePrepStmts", "true")
    config.addDataSourceProperty("prepStmtCacheSize", "250")
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    connectionInitSqlOpt.foreach(that => config.addDataSourceProperty("connectionInitSql", that))
    config
  }
}
