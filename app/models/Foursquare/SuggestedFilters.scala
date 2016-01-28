package models.Foursquare

import play.api.libs.json.{Format, Reads, JsPath, Writes}
import play.api.libs.functional.syntax._

case class SuggestedFilters(header: String, filters: Seq[Filter])

object SuggestedFilters{
  implicit val reads: Reads[SuggestedFilters] = (
    (JsPath \ "header").read[String] and
      (JsPath \ "filters").read[Seq[Filter]]
    )(SuggestedFilters.apply _)

  implicit val writes: Writes[SuggestedFilters] = (
    (JsPath \ "header").write[String] and
      (JsPath \ "filters").write[Seq[Filter]]
    )(unlift(SuggestedFilters.unapply))

  implicit val format: Format[SuggestedFilters] = Format(reads, writes)
}
