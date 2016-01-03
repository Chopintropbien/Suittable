package models

import play.api.libs.json.Json
import play.api.data.{Form, Mapping}
import play.api.data.Forms._


case class Search(near: String)

case class SimpleSearch(near: String, query: String)

object SimpleSearch{
  implicit val format = Json.format[SimpleSearch]

  def simpleSearchForm: Form[SimpleSearch] = Form{
    mapping("near" -> nonEmptyText,
      "query" -> text
    )(SimpleSearch.apply)(SimpleSearch.unapply)
  }

}

