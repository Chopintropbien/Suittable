package models.Foursquare

import play.api.libs.json.{Json, Format}

case class Item(venue: CompactVenue, referralId: String) // not complete

object Item{
  implicit val format: Format[Item] = Json.format[Item]

}
