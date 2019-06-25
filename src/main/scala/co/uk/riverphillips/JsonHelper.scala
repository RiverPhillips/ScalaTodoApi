package co.uk.riverphillips

import java.time.LocalDate

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import co.uk.riverphillips.models.Todo
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat, _}


object DateMarshalling {
  implicit object LocalDateFormat extends JsonFormat[LocalDate] {
    override def read(json: JsValue)= json match {

      case JsString(rawDate) => LocalDate.parse(rawDate)
      case error: JsValue =>
        deserializationError(s"Expected JsString, got $error")
    }

    override def write(obj: LocalDate): JsValue = JsString(obj.toString)
  }
}

trait JsonHelper extends SprayJsonSupport with DefaultJsonProtocol {
  import DateMarshalling._

  implicit val todoFormat = jsonFormat4(Todo.apply)
}
