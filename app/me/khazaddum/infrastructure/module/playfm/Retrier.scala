package me.khazaddum.infrastructure.module.playfm

import java.util.concurrent.TimeUnit

import javax.inject.{ Inject, Singleton }
import akka.actor._
import cats.syntax.option._
import com.google.inject.AbstractModule
import monix.execution.Cancelable
import play.api.inject.ApplicationLifecycle
import play.api.{ Configuration, Logger }
import play.api.Logger.logger

import scala.concurrent.duration._
import scala.concurrent.{ ExecutionContext, ExecutionContextExecutor, Future }
import scala.util.Try

trait Retrier {
  val actorSystem: ActorSystem
}

@Singleton
class RetrierWithActorSystem @Inject() ( val actorSystem: ActorSystem, lifecycle: ApplicationLifecycle ) extends Retrier {

  implicit val ec: ExecutionContext = actorSystem.dispatchers.lookup( "contexts.cache-operations" )
  val akkaScheduler: Scheduler = actorSystem.scheduler

  private var counter = 0

  logger.info( s"RetrierWithActorSystem... starting..." )

  val retryBlock: Cancellable = akkaScheduler.schedule( 10 seconds, 10 seconds ) {
    counter += 1
    logger.info( s"RetrierWithActorSystem try number $counter" )
  }

  lifecycle.addStopHook { () =>

    logger.info( s"RetrierWithActorSystem... stopping..." )
    retryBlock.cancel()

    actorSystem.terminate().map { terminated =>
      logger.info( s"RetrierWithActorSystem... (ok) ( $terminated )" )
    }

  }

}

@Singleton
class RetrierWithTasks @Inject() ( val actorSystem: ActorSystem, lifecycle: ApplicationLifecycle ) extends Retrier {

  import monix.execution.Scheduler

  implicit val executionContext: ExecutionContext = actorSystem.dispatchers.lookup( "contexts.cache-operations" )
  implicit val monixScheduler = Scheduler( executionContext )

  private var counter = 0

  logger.info( s"RetrierWithTasks... starting..." )

  val retryBlock: Cancelable = monixScheduler.scheduleWithFixedDelay( 3, 5, TimeUnit.SECONDS,
    new Runnable {
      def run(): Unit = {
        counter += 1
        logger.info( s"RetrierWithTasks try number $counter" )
      }
    } )

  lifecycle.addStopHook {
    () =>
      Future {
        logger.info( s"RetrierWithTasks... stopping..." )
        retryBlock.cancel()
        logger.info( s"RetrierWithTasks... (ok)" )
      }
  }

}