package models.Foursquare

import akka.io.Tcp.Write
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class CenterLatLng(lat: Double, lng: Double)

object CenterLatLng{
  implicit val format: Format[CenterLatLng] = Json.format[CenterLatLng]
}
