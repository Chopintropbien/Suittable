package models.Foursquare

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, Reads, JsPath, Writes}

case class SuggestedVenue(id: String, name: String, location: Location, categories: Seq[Category])


object SuggestedVenue{
  implicit val reads:  Reads[SuggestedVenue] = (
      (JsPath \ "id").read[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "location").read[Location] and
      (JsPath \ "categories").read[Seq[Category]]
    )(SuggestedVenue.apply _)

  implicit val writes: Writes[SuggestedVenue] = (
    (JsPath \ "id").write[String] and
      (JsPath \ "name").write[String] and
      (JsPath \ "location").write[Location] and
      (JsPath \ "categories").write[Seq[Category]]
    )(unlift(SuggestedVenue.unapply))

  implicit val format: Format[SuggestedVenue] = Format(reads, writes)

}
