package me.khazaddum.application

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class HomeController @Inject() ( cc: ControllerComponents ) extends AbstractController( cc ) {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok( "Welcome to Play" ) as TEXT
  }

}
