package me.khazaddum.infrastructure.module

import akka.actor.ActorSystem
import com.google.inject.Singleton
import akka.stream.Materializer
import javax.inject.Inject
import play.api.Logger.logger
import play.api.mvc.{ Filter, RequestHeader, Result }
import play.api.libs.json.Json
import javax.inject.Inject
import me.khazaddum.infrastructure.module.playfm.LoggingFilter
import play.api.http.DefaultHttpFilters
import play.api.http.EnabledFilters
import play.filters.cors.CORSFilter
import play.filters.gzip.GzipFilter

import scala.concurrent.{ ExecutionContext, Future }

@Singleton
class CustomFilters @Inject() ( corsFilter: CORSFilter, loggingFilter: LoggingFilter ) extends DefaultHttpFilters( corsFilter, loggingFilter )

