package models

import play.api.libs.json.Json
import play.api.data.{Form, Mapping}
import play.api.data.Forms._


case class Explore(near: String)

case class SimpleExplore(near: String, query: String)

object SimpleExplore{
  implicit val format = Json.format[SimpleExplore]

  def simpleExploreForm: Form[SimpleExplore] = Form{
    mapping("near" -> nonEmptyText,
      "query" -> text
    )(SimpleExplore.apply)(SimpleExplore.unapply)
  }

}

