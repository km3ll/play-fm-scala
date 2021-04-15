package me.khazaddum.infrastructure.module.playfm

import scala.concurrent.Future
import javax.inject._
import play.api.Logger.logger
import play.api.inject.ApplicationLifecycle

trait CustomStopHook

class ConnectionHook @Inject() ( lifecycle: ApplicationLifecycle ) extends CustomStopHook {

  logger.info( s"CustomStopHook connecting... (ok)" )

  lifecycle.addStopHook { () =>
    logger.info( s"CustomStopHook closing connections... (ok)" )
    Future.successful( s"connection closed." )
  }

}
