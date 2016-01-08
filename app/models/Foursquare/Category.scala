package models.Foursquare

import play.api.libs.json.{Format, Reads, JsPath, Writes}
import play.api.libs.functional.syntax._


case class Category(id: String,
                    name: String,
                    pluralName: String,
                    shortName: String,
                    icon: Icon,
                    primary: Option[Boolean],
                    categories: Option[Seq[Category]])

object Category{
  implicit val reads: Reads[Category] = (
    (JsPath \ "id").read[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "pluralName").read[String] and
      (JsPath \ "shortName").read[String] and
      (JsPath \ "icon").read[Icon] and
      (JsPath \ "primary").readNullable[Boolean] and
      (JsPath \ "categories").lazyReadNullable(Reads.seq[Category](reads))
    )(Category.apply _)

  implicit val writes: Writes[Category] = (
    (JsPath \ "id").write[String] and
      (JsPath \ "name").write[String] and
      (JsPath \ "pluralName").write[String] and
      (JsPath \ "shortName").write[String] and
      (JsPath \ "icon").write[Icon] and
      (JsPath \ "primary").writeNullable[Boolean] and
      (JsPath \ "categories").lazyWriteNullable(Writes.seq[Category](writes))
    )(unlift(Category.unapply))

  implicit val format: Format[Category] = Format(reads, writes)


}
