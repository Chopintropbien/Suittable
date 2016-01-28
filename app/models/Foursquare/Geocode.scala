package models.Foursquare

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Geocode(what: String,
                   where: String,
                   center: CenterLatLng,
                   displayString: String,
                   cc: String,
                   geometry: Bounds,
                   slug: String,
                   longId: String)

object Geocode{
  implicit val reads: Reads[Geocode] = (
    (JsPath \ "what").read[String] and
    (JsPath \ "where").read[String] and
    (JsPath \ "center").read[CenterLatLng] and
    (JsPath \ "displayString").read[String] and
    (JsPath \ "cc").read[String] and
    (JsPath \ "geometry" \ "bounds").read[Bounds] and
    (JsPath \ "slug").read[String] and
    (JsPath \ "longId").read[String]
    )(Geocode.apply _)

  implicit val writes: Writes[Geocode] = (
    (JsPath \ "what").write[String] and
    (JsPath \ "where").write[String] and
    (JsPath \ "center").write[CenterLatLng] and
    (JsPath \ "displayString").write[String] and
    (JsPath \ "cc").write[String] and
    (JsPath \ "geometry" \ "bounds").write[Bounds] and
    (JsPath \ "slug").write[String] and
    (JsPath \ "longId").write[String]
    )(unlift(Geocode.unapply))

  implicit val format: Format[Geocode] = Format(reads, writes)
}




