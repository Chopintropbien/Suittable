package controllers

import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import java.net.URLEncoder

import models.SimpleExplore._
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

  private val hostUrl = "https://api.foursquare.com/"
  private val authUrlAttribute =
    "?client_id=" + URLEncoder.encode(clientID, "UTF-8") +
    "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8")+
    "&v=" + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance.getTime) +
    "&m=foursquare"
  private def url(path: String) = hostUrl + path + authUrlAttribute

  private val exploreUrl = url("v2/venues/explore")
  private val categoriesUrl = url("v2/venues/categories")
  private def photoVenueUrl(venueId: String) = url("v2/venues/"+ venueId +"/photos")
  private val suggestcompletionUrl = url("v2/venues/suggestcompletion")


  private val DEFAULT_RADIUS = 2000
  private val DEFAULT_LIMIT = 20


  /**
   * Get the expected response off the url
   * @param url the url we want to connect
   * @return Get the expected response off the url
   */
  def getResponseUrl(url: String): Future[WSResponse] = ws.url(url).get // TODO: error quand il n'y a pas de connection internet

  /**
   * Get the response for a foursquare explore
   * @param near A string naming a place in the world.
   * @param query A term to be searched against a venue's tips, category, etc.
   * @param radius Radius to search within, in meters. If radius is not specified,
   *               a suggested radius will be used based on the density of venues in the area.
   * @param categoriesId
   * @return
   */
  def explore(near: String, query: String, radius: Int, categoriesId: String = ""): Future[WSResponse] = {
    val url = exploreUrl + "&near=" + URLEncoder.encode(near, "UTF-8") +
      "&query=" + URLEncoder.encode(query, "UTF-8") +
      "&radius=" + radius +
      "&categoryId=52e81612bcbc57f1066b79eb" + categoriesId +
      "&limit=" + DEFAULT_LIMIT
    getResponseUrl(url)
  }

  /**
   * Search the main photo to represent a venue.
   * @param venueId The foursquare Id of the venue
   * @return The main photo of this venue, if it does not exist, then the default one for a venue
   */
  def miniaturePhotoUrl(venueId: String): Photo = {
    val url = photoVenueUrl(venueId) + "&limit=1"
    val r = Await.result(getResponseUrl(url), 10000 milliseconds)

    (r.json \ "response" \ "photos" \ "items")(0).asOpt[Photo] match {
      case None => Photo("", 0, "", "", "") // TODO: Mettre une photo en cas de non photo
      case Some(photo) => photo
    }
  }



  def simpleExplore = Action { implicit request =>
    simpleExploreForm.bindFromRequest.fold(
      errorForm => Ok("d"), // TODO
      simpleExploreData => {
        val r = Await.result(explore(simpleExploreData.near, simpleExploreData.query, DEFAULT_RADIUS), 10000 millisecond)
//        Ok(Json.prettyPrint(r.json))
        (r.json \ "response").asOpt[ResponseExplore] match {
          case None => Ok("Bib problem") // TODO: Si le serveur Foursquare a des probleme ou si il arrive pas a trouver la reponse
          case Some(responseExplore) => {
            /* filter the venues to be sure they have all the information needed for the display:
                - lat and lng exist
             */
            val filteredVenue: Seq[(CompactVenue, Photo)] =
              responseExplore.groups.flatMap{ g =>
                g.items.collect {
                  case i if (i.venue.location.lat.isDefined && i.venue.location.lng.isDefined) => {
                    (i.venue, miniaturePhotoUrl(i.venue.id))
                  }
                }
              }
            Ok(views.html.explore.explore(filteredVenue, responseExplore.geocode, foodCategories))
          }
        }
      }
    )
  }

  /**
   * Put all the existing food foursquare categories in one Seq
   * @return All the food foursquare Categories
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


  /**
   * Provide suggestion of possible venues the user want when he enter the letter contained in the query
   * This is of the autocompletion
   * @param near where the user is located
   * @param query the letter the user enter for a search
   * @return  the response give by the foursquare server
   */
  def suggestion(near: String, query: String): Future[WSResponse] = {
    val url = suggestcompletionUrl + "&near=" + URLEncoder.encode(near, "UTF-8") +
                                     "&query=" + URLEncoder.encode(query, "UTF-8")
    getResponseUrl(url)
  }

  def autoCompletion(near: String, query: String) = Action{ implicit request =>
    val r = Await.result(suggestion(near, query), 1000000 millisecond)
    (r.json \ "response" \ "minivenues").asOpt[Seq[SuggestedVenue]] match {
      case None => Ok("Probleme!!!") // TODO: ben faire le plus chiant... les erreurs +
      case Some(venues) => Ok(Json.prettyPrint(Json.toJson(venues.take(10))))
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
    def url = exploreUrl + "&ll=40.7,-74"

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
