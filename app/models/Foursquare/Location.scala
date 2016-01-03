package models.Foursquare

import play.api.libs.json.{Format, Reads, JsPath, Writes}
import play.api.libs.functional.syntax._

case class Location(address: Option[String],
                    crossStreet: Option[String],
                    lat: Option[Double],
                    lng: Option[Double],
                    postalCode: Option[String],
                    cc: Option[String],
                    neighborhood: Option[String],
                    city: Option[String],
                    state: Option[String],
                    country: Option[String],
                    formattedAddress: Option[Seq[String]],
                    distance: Option[Double]){


  def formatAddress() = {
    address.getOrElse("")
  }
}

object Location{

  implicit val reads: Reads[Location] = (
    (JsPath \ "address").readNullable[String] and
    (JsPath \ "crossStreet").readNullable[String] and
    (JsPath \ "lat").readNullable[Double] and
    (JsPath \ "lng").readNullable[Double] and
    (JsPath \ "postalCode").readNullable[String] and
    (JsPath \ "cc").readNullable[String] and
    (JsPath \ "neighborhood").readNullable[String] and
    (JsPath \ "city").readNullable[String] and
    (JsPath \ "state").readNullable[String] and
    (JsPath \ "country").readNullable[String] and
    (JsPath \ "formattedAddress").readNullable[Seq[String]] and
    (JsPath \ "distance").readNullable[Double]
    )(Location.apply _)

  implicit val writes: Writes[Location] = (
    (JsPath \ "address").writeNullable[String] and
    (JsPath \ "crossStreet").writeNullable[String] and
    (JsPath \ "lat").writeNullable[Double] and
    (JsPath \ "lng").writeNullable[Double] and
    (JsPath \ "postalCode").writeNullable[String] and
    (JsPath \ "cc").writeNullable[String] and
    (JsPath \ "neighborhood").writeNullable[String] and
    (JsPath \ "city").writeNullable[String] and
    (JsPath \ "state").writeNullable[String] and
    (JsPath \ "country").writeNullable[String] and
    (JsPath \ "formattedAddress").writeNullable[Seq[String]] and
    (JsPath \ "distance").writeNullable[Double]
    )(unlift(Location.unapply))

  implicit val format: Format[Location] = Format(reads, writes)

}