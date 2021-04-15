package me.khazaddum.infrastructure.module

import play.api.Logger.logger
import play.api.{ ApplicationLoader, Logger }
import play.api.inject.guice.{ GuiceApplicationBuilder, GuiceApplicationLoader }

class CustomApplicationLoader extends GuiceApplicationLoader() {

  override def builder( context: ApplicationLoader.Context ): GuiceApplicationBuilder = {

    logger.info( "ApplicationLoader... (ok)" )

    initialBuilder
      .disableCircularProxies()
      .in( context.environment )
      .loadConfig( context.initialConfiguration )
      .overrides( overrides( context ): _* )

  }

}
