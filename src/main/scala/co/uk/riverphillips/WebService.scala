package co.uk.riverphillips

import java.time.{LocalDate, Month}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import co.uk.riverphillips.models.Todo
import co.uk.riverphillips.repository.{TodoRepository, TodoRepositoryImplementation}
import com.google.inject.{AbstractModule, Guice}

object WebService extends App {
  private val injector = Guice.createInjector(new AbstractModule() {
    override def configure() {
      bind(classOf[TodoRepository]).to(classOf[TodoRepositoryImplementation])
    }
  })

  private val todoService = injector.getInstance(classOf[Routes])

  implicit val System = ActorSystem()
  implicit val materializer = ActorMaterializer()

  implicit val dispatcher = System.dispatcher

  private val todoRepository: TodoRepository = new TodoRepositoryImplementation

    todoRepository.ddl.onComplete { _ =>
    todoRepository.createTodo(Todo("Ride bike", LocalDate.of(2019, Month.AUGUST, 1), false))
    Http().bindAndHandle(todoService.todoRoutes, "localhost", 9000)
    println("Server has started")
  }
}



