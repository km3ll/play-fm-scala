package me.khazaddum.infrastructure.module.playfm

import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject.Inject
import play.api.Logger.logger
import play.api.mvc.{ Filter, RequestHeader, Result }

import scala.concurrent.{ ExecutionContext, Future }

class LoggingFilter @Inject() ( implicit val mat: Materializer, system: ActorSystem ) extends Filter {

  implicit val ec: ExecutionContext = system.dispatchers.lookup( "contexts.user-operations" )

  def apply( nextFilter: RequestHeader => Future[Result] )( requestHeader: RequestHeader ): Future[Result] = {

    val startTime = System.currentTimeMillis

    nextFilter( requestHeader ).map { result =>
      val responseTime: Long = System.currentTimeMillis - startTime
      logger.info( s"CustomFilter... (ok) response time: $responseTime millis " )

      result.withHeaders( "response-time-in-millis" -> responseTime.toString )
    }

  }

}
