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


  private val accessTokenUrl = "https://foursquare.com/oauth2/access_token"
  private val authorizeUrl = "https://foursquare.com/oauth2/authorize"
  private val searchUrl = "https://api.foursquare.com/v2/venues/search?" + authUrlAttribute


  def search(near: String, query: String) = {
    val url = searchUrl + "&near=" + URLEncoder.encode(near, "UTF-8") + "&query=" + URLEncoder.encode(query, "UTF-8")
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
            Ok(views.html.search.search(venues))
          }
        }
      }
    )
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
 */
