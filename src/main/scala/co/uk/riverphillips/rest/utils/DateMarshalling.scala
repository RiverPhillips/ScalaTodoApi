package co.uk.riverphillips.rest.utils

import java.text.SimpleDateFormat
import java.util.Date
import spray.json._

import scala.util.Try

object DateMarshalling {
  implicit object DateFormat extends JsonFormat[Date] {
    override def write(date: Date): JsValue = JsString(dateToIsoString(date))

    override def read(json: JsValue)= json match {
      case JsString(rawDate) => parseIsoDateString(rawDate)
        .fold(deserializationError(s"Expected ISO date format, got $rawDate"))(identity)
      case error: JsValue =>
        deserializationError(s"Expected JsString, got $error")
    }
  }

  private val localIsoDateFormatter: ThreadLocal[SimpleDateFormat] = new ThreadLocal[SimpleDateFormat] {
    override def initialValue(): SimpleDateFormat =
      new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  }

  private def dateToIsoString(date: Date): String ={
    localIsoDateFormatter.get().format(date)
  }

  private def parseIsoDateString(date: String): Option[Date] = {
    Try{
      localIsoDateFormatter.get().parse(date)
    }.toOption
  }
}
