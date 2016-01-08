package controllers

import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import java.net.URLEncoder

import models.SimpleSearch._
import play.api.libs.json.Json
import play.api.libs.oauth.{RequestToken, ConsumerKey}
import play.api.libs.ws.{WSResponse, WSClient}
import play.api.mvc.{Action, Controller}
import scala.concurrent._
import scala.concurrent.duration._

import play.api.libs.json._


import scala.concurrent.Future

import models.Foursquare._


class Foursquare @Inject() (ws: WSClient) extends Controller{

  // TODO: Change the password and not connect with facebook et proteger la cles et eetre pres a la changer
  private val clientID = "LMKLGZJFEJLA4Q1N45KFTAWWCIYHCLYCGYJC5XRSCGOLVGOK"
  private val clientSecret = "J21P11PTLSF2ZFUKY050C54NRGBT3XEI20IL1RETP0VH3NVO"

  private val authUrlAttribute = "client_id=" + URLEncoder.encode(clientID, "UTF-8") +
    "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8")+
    "&v=" + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance.getTime)

  private val hostUrl = "https://api.foursquare.com/"

  private val accessTokenUrl = "https://foursquare.com/oauth2/access_token"
  private val authorizeUrl = "https://foursquare.com/oauth2/authorize"
  private val searchUrl = hostUrl + "v2/venues/search?" + authUrlAttribute

  private val categoriesUrl = hostUrl + "v2/venues/categories?" + authUrlAttribute


  // categoriesId : ,Id1,id2
  def search(near: String, query: String, categoriesId: String = "") = {
    val url = searchUrl + "&near=" + URLEncoder.encode(near, "UTF-8") +
      "&query=" + URLEncoder.encode(query, "UTF-8") +
      "&categoryId=4d4b7105d754a06374d81259" + categoriesId
    ws.url(url).get // TODO: error
  }

  def simpleSearch = Action { implicit request =>
    simpleSearchForm.bindFromRequest.fold(
      errorForm => Ok("d"),
      simpleSearchData => {
        val r = Await.result(search(simpleSearchData.near, simpleSearchData.query), 10000 millisecond)
        (r.json \ "response" \ "venues").asOpt[Seq[CompactVenue]] match {
          case None => Ok("Bib problem") // TODO
          case Some(venues) => {
//            Ok(Json.prettyPrint(r.json))
            Ok(views.html.search.search(venues, foodCategories))
          }
        }
      }
    )
  }

  /**
   *
   * @return All the food Categories
   */
  def foodCategories: Seq[Category] = {
    val c = Await.result(ws.url(categoriesUrl).get, 100000 milliseconds)
    (c.json \ "response" \ "categories").asOpt[Seq[Category]] match {
      case None => Array[Category]()
      case Some(categories) => {
        val food = categories.filter(p => p.id == "4d4b7105d754a06374d81259")
        if(food.isEmpty){
          Array[Category]()
        }
        else food.head.categories.get
      }
    }
  }

  def displayCategories = Action{ implicit request =>
    val c = Await.result(ws.url(categoriesUrl).get, 100000 milliseconds) // TODO: error

    (c.json \ "response" \ "categories").asOpt[Seq[Category]] match {
      case None => {
        Ok("d")
      } // TODO
      case Some(categories) => {
//        Ok(Json.prettyPrint(c.json))
        Ok(Json.prettyPrint(Json.toJson(categories)))
      }
    }

  }




  def test = Action{

    // TODO: Pas plua protege ????
    def url = searchUrl + "&ll=40.7,-74"

    val r1: Future[WSResponse] = ws.url(url).get
    val r = Await.result(r1, 10000000 millisecond)
    Ok(Json.prettyPrint(r.json))
  }

}


/* Oauth 1

  private val tokenKey = "PnlvDm895fgVbppnl1Vd6S2Lqf9ZJgZY"
  private val tokenSecret = "dImjvkjCVjU72y_Rqmuvmi-cFbU"
  private val consumerKey = "g11-fCdGhrKCrPyoQmOnTA"
  private val consumerSecret = "4zhEXy3sL942U5078RhTbgrpTp0"

  val KEY = ConsumerKey(consumerKey, consumerSecret)
  val TOKEN = RequestToken(tokenKey, tokenSecret)


  def sign(url: String): Future[WSResponse] = {
    ws.url(url).sign(OAuthCalculator(KEY, TOKEN)).get
  }


    val basicCategories = Array(("4bf58dd8d48988d1c8941735", "African Restaurant"),
    ("4bf58dd8d48988d14e941735", "American"),
    ("4bf58dd8d48988d142941735", "Asian"),
    ("4bf58dd8d48988d145941735", "Chinese"),
    ("4bf58dd8d48988d111941735", "Japanese"),
    ("4bf58dd8d48988d1d2941735", "Sushi"),
    ("4bf58dd8d48988d149941735", "Thai"),
    ("4bf58dd8d48988d14a941735", "Vietnamese"),
    ("4bf58dd8d48988d16a941735", "Bakery"),
    ("52e81612bcbc57f1066b79f1", "Bistro"),
    ("52e81612bcbc57f1066b79f1", "Bistro"),
    ("4bf58dd8d48988d17a941735", "Cajun / Creole"),
    ("4bf58dd8d48988d1e0931735", "Coffee"),
    ("52e81612bcbc57f1066b79f2", "Creperie"),
    ("4bf58dd8d48988d1d0941735", "Ice Cream"),
    ("", ""),
    ("", ""),
    ("", ""),
    ("", ""),
    ("", ""),
    ("", ""),
    ("", ""))
  val advanceCategories = Array(("503288ae91d4c4b30a586d67", "Afghan Restaurant"),
    ("4bf58dd8d48988d10a941735", "Ethiopian"),
    ("4bf58dd8d48988d113941735", "Korean"),
    ("4bf58dd8d48988d1df931735", "BBQ"),
    ("52e81612bcbc57f1066b7a02", "Belgian"),
    ("4bf58dd8d48988d16b941735", "Brazilian"),
    ("52e81612bcbc57f1066b7a0c", "Bubble Tea"),
    ("52e81612bcbc57f1066b79f4", "Buffet"),
    ("4bf58dd8d48988d16c941735", "Burger"),
    ("4bf58dd8d48988d16d941735", "Café"),
    ("5293a7d53cf9994f4e043a45", "Caucasian"),
    ("4bf58dd8d48988d1d0941735", "Dessert"),
    ("", ""),
    ("", ""),
    ("", ""),
    ("", ""),
    ("", ""),
    ("", ""),
    ("", ""))


 */
