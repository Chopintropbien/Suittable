package models.Foursquare

import play.api.libs.json.{Format, Reads, JsPath, Writes}
import play.api.libs.functional.syntax._

case class Contact(phone: Option[String],
                   formattedPhone: Option[String],
                   twitter: Option[String],
                   facebook: Option[String],
                   facebookName: Option[String],
                   facebookUsername: Option[String])


object Contact{
  implicit val read: Reads[Contact] = (
    (JsPath \  "phone").readNullable[String] and
      (JsPath \  "formattedPhone").readNullable[String] and
      (JsPath \  "twitter").readNullable[String] and
      (JsPath \  "facebook").readNullable[String] and
      (JsPath \  "facebookName").readNullable[String] and
      (JsPath \  "facebookUsername").readNullable[String]
    )(Contact.apply _)


  implicit val write: Writes[Contact] = (
    (JsPath \  "phone").writeNullable[String] and
      (JsPath \  "formattedPhone").writeNullable[String] and
      (JsPath \  "twitter").writeNullable[String] and
      (JsPath \  "facebook").writeNullable[String] and
      (JsPath \  "facebookName").writeNullable[String] and
      (JsPath \  "facebookUsername").writeNullable[String]
    )(unlift(Contact.unapply))

  implicit val format: Format[Contact] = Format(read, write)
}
