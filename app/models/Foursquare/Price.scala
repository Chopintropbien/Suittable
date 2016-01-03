package models.Foursquare

import play.api.libs.json.{Format, Reads, JsPath, Writes}
import play.api.libs.functional.syntax._

case class Price(tier: Int, message: String)

object Price{
  implicit val reads: Reads[Price] = (
    (JsPath \ "tier").read[Int] and
      (JsPath \ "message").read[String]
    )(Price.apply _)

  implicit val writes: Writes[Price] = (
    (JsPath \ "tier").write[Int] and
      (JsPath \ "message").write[String]
    )(unlift(Price.unapply))

  implicit val format: Format[Price] = Format(reads, writes)
}