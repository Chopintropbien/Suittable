package models.Foursquare

import play.api.libs.json.{Format, Reads, JsPath, Writes}
import play.api.libs.functional.syntax._

case class CompleteVenue(id: String,
                         name: String,
                         contact: Contact,
                         location: Location,
                         categories: Seq[Category],
                         verified: Boolean,
                         stats: Stats,
                         price: Option[Price],
                         rating: Option[Double])
