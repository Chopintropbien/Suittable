package controllers

import play.api._
import play.api.mvc._
import models.SimpleSearch._

class Application extends Controller {



  def index = Action{ implicit request =>
    Ok(views.html.index.index(simpleSearchForm))
  }

  def single = Action{ implicit request =>
    Ok(views.html.single.single())
  }

  def search = Action{ implicit request =>
//    Ok(views.html.search.search()) // TODO: Set default setting
    Ok("ddd")
  }

  def profile = Action{ implicit request =>
    Ok(views.html.profile.profile())
  }

  def writeReview = Action{ implicit request =>
    Ok(views.html.writeReview.writeReview())
  }




}
