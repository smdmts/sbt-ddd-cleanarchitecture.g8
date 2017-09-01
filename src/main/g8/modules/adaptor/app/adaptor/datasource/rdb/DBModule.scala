package adaptor.datasource.rdb

import scaldi.Module
import scalikejdbc.DBSession

/**
  * DBModule.
  */
class DBModule(implicit session: DBSession) extends Module {}
