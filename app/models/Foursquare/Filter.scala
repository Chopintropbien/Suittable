package models.Foursquare

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Filter(name: String, key: String)

object Filter{
  implicit val format: Format[Filter] = Json.format[Filter]
}
