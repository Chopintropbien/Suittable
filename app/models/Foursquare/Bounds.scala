package models.Foursquare

import akka.io.Tcp.Write
import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Bounds(ne: CenterLatLng, sw: CenterLatLng)

object Bounds{

  implicit val reads: Reads[Bounds] = (
    (JsPath \ "ne").read[CenterLatLng] and
      (JsPath \ "sw").read[CenterLatLng]
    )(Bounds.apply _)

  implicit val writes: Writes[Bounds] = (
    (JsPath \ "ne").write[CenterLatLng] and
      (JsPath \ "sw").write[CenterLatLng]
    )(unlift(Bounds.unapply))

  implicit val format: Format[Bounds] = Format(reads, writes)
}
