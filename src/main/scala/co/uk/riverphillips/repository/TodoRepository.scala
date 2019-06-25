package co.uk.riverphillips.repository

import java.time.LocalDate

import co.uk.riverphillips.dataAccess.{DataAccessComponent, H2DBDataAccessComponent}
import co.uk.riverphillips.models.Todo
import com.google.inject.Singleton
import slick.basic.DatabasePublisher

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait TodoRepository extends TodoTable {
  this: DataAccessComponent =>

  import profile.api._

  def createTodo(todo: Todo): Future[Int] = db.run {
    todoTableAutoIncrement += todo
  }

  def updateTodo(todo: Todo): Future[Int] = db.run {
    todoTableQuery.filter(_.id=== todo.id.get).update(todo)
  }

  def getAllTodos: Future[Seq[Todo]] = db.run{
    todoTableQuery.result
  }

  def getTodoById(id: Int):Future[Option[Todo]] = db.run {
    todoTableQuery.filter(_.id === id ).result.headOption
  }

  def deleteTodoById(id: Int): Future[Int] = db.run { todoTableQuery.filter(_.id === id).delete }

  def ddl=db.run {todoTableQuery.schema.createIfNotExists}
}

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

@Singleton
class TodoRepositoryImplementation extends TodoRepository with H2DBDataAccessComponent

