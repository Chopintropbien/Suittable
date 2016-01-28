package models.Foursquare

import play.api.libs.json.Json

/**
 * From https://developer.foursquare.com/docs/responses/photo
 *
 * @param id A unique string identifier for this photo.
 * @param createdAt Seconds since epoch when this photo was created.
 * @param prefix Start of the URL for this photo.
 * @param suffix Ending of the URL for this photo.
 * @param visibility Can be one of "public" (everybody can see, including on the venue page),
 *                   "friends" (only the poster's friends can see), or "private" (only the poster can see)
 */
case class Photo(id: String, createdAt: Int, prefix: String, suffix: String, visibility: String){
  val miniature = prefix + Photo.squareSize(Photo.tinySize) + suffix
  val small = prefix + Photo.squareSize(Photo.smallSize) + suffix
  val medium = prefix + Photo.squareSize(Photo.mediumSize) + suffix
}

object Photo{
  implicit val format = Json.format[Photo]

  val tinySize = "36"
  val smallSize = "100"
  val mediumSize = "300"
  val bigSize = "500"

  val public = "public"
  val friends = "friends"
  val private_ = "private"

  def size(width: String, height: String) = width + "x" + height
  def squareSize(edgeLength: String) = size(edgeLength, edgeLength)


}
