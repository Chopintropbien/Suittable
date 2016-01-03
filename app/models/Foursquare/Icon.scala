package models.Foursquare

import play.api.libs.json.{Format, Reads, JsPath, Writes}
import play.api.libs.functional.syntax._

case class Icon(prefix: String, suffix: String)

object Icon{
  implicit val reads: Reads[Icon] = (
    (JsPath \ "prefix").read[String] and
    (JsPath \ "suffix").read[String]
    )(Icon.apply _)

  implicit val writes: Writes[Icon] =  (
    (JsPath \ "prefix").write[String] and
    (JsPath \ "suffix").write[String]
    )(unlift(Icon.unapply))

  implicit val format: Format[Icon] = Format(reads, writes)
}