package models.Foursquare

import play.api.libs.json.{Format, Reads, JsPath, Writes}
import play.api.libs.functional.syntax._


case class CompactVenue(id: String,
                        name: String,
                        contact: Contact,
                        location: Location,
                        categories: Seq[Category],
                        verified: Boolean,
                        stats: Stats,
                        url: Option[String],
                        price: Option[Price],
                        rating: Option[Double])


object CompactVenue{
  implicit val reads: Reads[CompactVenue] = (
      (JsPath \ "id").read[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "contact").read[Contact] and
      (JsPath \ "location").read[Location] and
      (JsPath \ "categories").read[Seq[Category]] and
      (JsPath \ "verified").read[Boolean] and
      (JsPath \ "stats").read[Stats] and
      (JsPath \ "url").readNullable[String] and
      (JsPath \ "price").readNullable[Price] and
      (JsPath \ "rating").readNullable[Double]
    )(CompactVenue.apply _)

  implicit val writes: Writes[CompactVenue] = (
    (JsPath \ "id").write[String] and
      (JsPath \ "name").write[String] and
      (JsPath \ "contact").write[Contact] and
      (JsPath \ "location").write[Location] and
      (JsPath \ "categories").write[Seq[Category]] and
      (JsPath \ "verified").write[Boolean] and
      (JsPath \ "stats").write[Stats] and
      (JsPath \ "url").writeNullable[String] and
      (JsPath \ "price").writeNullable[Price] and
      (JsPath \ "rating").writeNullable[Double]
    )(unlift(CompactVenue.unapply))

  implicit val format: Format[CompactVenue] = Format(reads, writes)
}


