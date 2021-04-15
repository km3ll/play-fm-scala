package me.khazaddum.application

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {

      val controller = new HomeController( stubControllerComponents() )
      val home = controller.index().apply( FakeRequest( GET, "/playfw" ) )

      status( home ) mustBe OK
      contentType( home ) mustBe Some( "text/plain" )
      contentAsString( home ) must include( "Welcome to Play" )
    }

  }
}
