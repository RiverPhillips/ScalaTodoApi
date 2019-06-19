package co.uk.riverphillips

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import co.uk.riverphillips.rest.TodoRoute

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

object WebServer extends App {
  implicit val system: ActorSystem = ActorSystem("todo-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = new TodoRoute().route

  val bindingFuture: Future[ServerBinding] = Http().bindAndHandle(route, "localhost", 8080)

  println("Akka http server running\nPress Enter to end")
  StdIn.readLine()

  bindingFuture.flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
