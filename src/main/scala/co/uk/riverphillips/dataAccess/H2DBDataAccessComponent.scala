package co.uk.riverphillips.dataAccess

import org.slf4j.LoggerFactory

trait H2DBDataAccessComponent extends DataAccessComponent {
  val logger = LoggerFactory.getLogger(this.getClass)

  val profile = slick.jdbc.H2Profile

  import profile.api._

  val h2Url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"

  val db: Database = {
    logger.info("Creating test connection ..................................")
    Database.forURL(url = h2Url, driver = "org.h2.Driver")
  }
}
