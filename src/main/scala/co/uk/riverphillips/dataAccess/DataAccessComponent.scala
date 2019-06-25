package co.uk.riverphillips.dataAccess

import slick.jdbc.JdbcProfile

trait DataAccessComponent {

  val profile: JdbcProfile

  import profile.api._

  val db: Database
}
