package me.khazaddum.application

import akka.actor.ActorSystem
import javax.inject._
import me.khazaddum.domain.repository.AccountRepository
import me.khazaddum.domain.service.AccountService
import me.khazaddum.infrastructure.module.playfm.CustomCache
import me.khazaddum.infrastructure.parser.JsonParsers
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class AccountController @Inject() ( cc: ControllerComponents, accountRepo: AccountRepository, system: ActorSystem, customCacheApi: CustomCache ) extends AbstractController( cc ) with JsonParsers {

  implicit val ec: ExecutionContext = system.dispatchers.lookup( "contexts.user-operations" )

  customCacheApi.load( Map( "rate.of.interest" -> "0,91%" ) )

  def getAccount( no: String ): Action[AnyContent] = Action.async {
    AccountService.findAccount( no ).run( accountRepo ).map {
      case Right( acc )                               => Ok( Json.toJson( acc ) )
      case Left( err ) if err.contains( "not found" ) => NotFound( s"account not found [ $no ]" )
      case Left( err )                                => InternalServerError( err )
    }
  }

  def getRateOfInterest: Action[AnyContent] = Action.async {
    customCacheApi.get( "rate.of.interest" ).map( data =>
      if ( data.nonEmpty ) Ok( data ) as TEXT
      else NotFound( "rate of interest is not defined" ) as TEXT )
  }

}
