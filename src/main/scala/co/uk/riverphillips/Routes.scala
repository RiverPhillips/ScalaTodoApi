package co.uk.riverphillips

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import co.uk.riverphillips.models.Todo
import co.uk.riverphillips.repository.TodoRepository
import com.google.inject.Inject

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Routes @Inject()(todoRepository: TodoRepository) extends JsonHelper {

  val todoRoutes = {
    get {
      path("todo" / IntNumber) { id =>
        onSuccess(todoRepository.getTodoById(id)) {
          case Some(todo) => complete(todo)
          case None => complete(StatusCodes.NotFound)
        }
      } ~
      path("todos") {
        complete(todoRepository.getAllTodos)
      }
    }~
    post {
      path("todo"){
        entity(as[Todo]){todo =>
          val todoId = Await.result(todoRepository.createTodo(todo), Duration(1, "seconds"))
          onSuccess(todoRepository.getTodoById(todoId)) {
            case Some(todo) => complete(todo)
            case None => complete(StatusCodes.NotFound)
          }
        }
      }
    }~
    put{
      path("todo") {
        entity(as[Todo]){todo =>
          onSuccess(todoRepository.updateTodo(todo)) {
            id => complete(HttpEntity(ContentTypes.`application/json`, s"$id"))
          }
        }
      }
    }~
    delete{
      path("todo" / IntNumber) { id =>
        todoRepository.deleteTodoById(id)
        complete(StatusCodes.NoContent)
      }
    }
  }
}
