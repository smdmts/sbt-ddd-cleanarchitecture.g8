package adaptor.http

import adaptor.datasource.rdb.DBModule
import modules.{Modules, UseCaseModule}
import scaldi.ImmutableWrapper
import scalikejdbc.DBSession

/**
  * DBSessionSupport.
  */
trait DBSessionSupport {
  def apply()(implicit session: DBSession) =
    new DBModule() :: new UseCaseModule :: ImmutableWrapper(Modules.staticModules)
}

object DBSessionModules extends DBSessionSupport
