package co.uk.riverphillips.rest

import java.util.Date

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.Unmarshaller
import co.uk.riverphillips.domain.Todo
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  import utils.DateMarshalling._

  implicit val todoFormat = jsonFormat4(Todo)
}

class TodoRoute extends JsonSupport {
  implicit val dateStringUnmarshaller: Unmarshaller[String, Date] =
    Unmarshaller.strict[String, Date] {
      import java.text.SimpleDateFormat
      val formatter = new SimpleDateFormat("yyyy-MM-dd")

      string: String => formatter.parse(string)
    }

  val route: Route = path("todo") {
    get {
      parameter("date".as[Date].?) {
        date: Option[Date] =>

            val mockTodos : Seq[Todo] = Seq(
              Todo(0, "first", new Date(0), complete =  false),
              Todo(1, "second", new Date(0), complete =  false),
              Todo(2, "third", new Date(0), complete =  false)
            )

            date match {
              case Some(_) => complete(mockTodos)
              case None => complete(mockTodos)
            }
      }
    } ~
      post {
        entity(as[Todo]){ _:Todo => complete(StatusCodes.Created)}
      }
  } ~
    path("todo" / IntNumber) {
      (todoId: Int) =>
        put {
          complete(StatusCodes.NoContent)
        }
    }
}

