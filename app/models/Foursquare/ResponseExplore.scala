package models.Foursquare

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ResponseExplore(suggestedFilters: Option[SuggestedFilters],
                           geocode: Geocode,
                           headerLocation: String,
                           headerFullLocation: String,
                           headerLocationGranularity: String,
                           query: Option[String],
                           totalResults: Int,
                           suggestedBounds: Option[Bounds],
                           groups: Seq[Group])

object ResponseExplore{
  implicit val reads: Reads[ResponseExplore] = (
    (JsPath \ "suggestedFilters").readNullable[SuggestedFilters] and
    (JsPath \ "geocode").read[Geocode] and
    (JsPath \ "headerLocation").read[String] and
    (JsPath \ "headerFullLocation").read[String] and
    (JsPath \ "headerLocationGranularity").read[String] and
    (JsPath \ "query").readNullable[String] and
    (JsPath \ "totalResults").read[Int] and
    (JsPath \ "suggestedBounds").readNullable[Bounds] and
    (JsPath \ "groups").read[Seq[Group]]
    )(ResponseExplore.apply _)

  implicit val writes: Writes[ResponseExplore] = (
    (JsPath \ "suggestedFilters").writeNullable[SuggestedFilters] and
    (JsPath \ "geocode").write[Geocode] and
    (JsPath \ "headerLocation").write[String] and
    (JsPath \ "headerFullLocation").write[String] and
    (JsPath \ "headerLocationGranularity").write[String] and
    (JsPath \ "query").writeNullable[String] and
    (JsPath \ "totalResults").write[Int] and
    (JsPath \ "suggestedBounds").writeNullable[Bounds] and
    (JsPath \ "group").write[Seq[Group]]
    )(unlift(ResponseExplore.unapply))

  implicit val format: Format[ResponseExplore] = Format(reads, writes)
}

