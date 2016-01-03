package models.Foursquare

import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, Writes, JsPath, Reads}


case class Stats(checkinsCount: Int, usersCount: Int, tipCount: Int)

object Stats{
  implicit val reads: Reads[Stats] = (
      (JsPath \ "checkinsCount").read[Int] and
      (JsPath \ "usersCount").read[Int] and
      (JsPath \ "tipCount").read[Int]
    )(Stats.apply _)

  implicit val writes: Writes[Stats] = (
      (JsPath \ "is_claimed").write[Int] and
      (JsPath \ "usersCount").write[Int] and
      (JsPath \ "tipCount").write[Int]
    )(unlift(Stats.unapply))


  implicit val format: Format[Stats] = Format(reads, writes)
}
