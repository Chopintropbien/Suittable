package models.Foursquare

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, Reads, JsPath, Writes}


case class Group(_type: String, name: String, items: Seq[Item])


object Group{
  implicit val reads: Reads[Group] = (
    (JsPath \ "type").read[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "items").read[Seq[Item]]
    )(Group.apply _)

  implicit val writes: Writes[Group] = (
    (JsPath \ "type").write[String] and
      (JsPath \ "name").write[String] and
      (JsPath \ "items").write[Seq[Item]]
    )(unlift(Group.unapply))

  implicit val format: Format[Group] = Format(reads, writes)
}





//
//case class Reasons(count: Int, items: )
//
//case class Tip(id: String,
//               createdAt: Int,
//               text: String,
//               _type: String,
//               canonicalUrl: String,
//               likes: Likes,
//               logView: Boolean,
//               todo: Todo,
//               user: User)
//
//
//case class Likes()
//
//case class Todo()
//
//case class User(id: String,
//                firstName: String,
//                gender: String,
//                photo: Photo)
//
//case class Photo()
