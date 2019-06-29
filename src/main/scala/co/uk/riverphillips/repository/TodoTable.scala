package co.uk.riverphillips.repository

import java.time.LocalDate

import co.uk.riverphillips.dataAccess.DataAccessComponent
import co.uk.riverphillips.models.Todo

class TodoTable {
  this: DataAccessComponent =>

  import profile.api._

  class TodoTable(tag: Tag) extends Table[Todo](tag, "todos") {

    def id = column[Int]("todoId", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def deadline = column[LocalDate]("deadline")

    def complete = column[Boolean]("complete")

    def * = (name, deadline, complete, id.?).mapTo[Todo]
  }

  protected val todoTableQuery = TableQuery[TodoTable]

  protected def todoTableAutoIncrement = todoTableQuery returning todoTableQuery.map(_.id)
}
