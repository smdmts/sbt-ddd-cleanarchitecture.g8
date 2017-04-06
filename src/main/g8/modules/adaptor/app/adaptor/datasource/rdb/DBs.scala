package adaptor.datasource.rdb

import scalikejdbc.NamedDB

/**
  * DBs.
  */
object DBs {
  def dmpdb = NamedDB('default)
}
