package co.uk.riverphillips.rest

import java.util.Date

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.Unmarshaller

class TodoRoute {
  implicit val dateStringUnmarshaller: Unmarshaller[String, Date] =
    Unmarshaller.strict[String, Date] {
      import java.text.SimpleDateFormat
      val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

      string: String => formatter.parse(string)
    }

  val route: Route = {
    path("todo") {
      get {
        parameter("date".as[Date].?) {
          (date: Option[Date]) =>
            ???
        }
      } ~
        post {
          ???
        }
    } ~
      path("todo" / IntNumber) {
        (todoId: Int) =>
          put {
            ???
          }
      }
  }
}

