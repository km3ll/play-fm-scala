package me.khazaddum.infrastructure.module.playfm

import akka.actor.ActorSystem
import javax.inject.Inject
import play.api.cache.AsyncCacheApi
import play.api.cache._
import play.api.cache.redis.CacheAsyncApi

import scala.concurrent.{ ExecutionContext, Future }

trait CustomCache {
  def load( values: Map[String, String] ): Unit
  def set( key: String, data: String ): Unit
  def get( key: String ): Future[String]
}

class PlayFrameworkCache @Inject() ( asyncCacheApi: AsyncCacheApi, system: ActorSystem ) extends CustomCache {

  implicit val ec: ExecutionContext = system.dispatchers.lookup( "contexts.cache-operations" )

  def load( values: Map[String, String] ): Unit = {
    values.foreach {
      case ( key, data ) => asyncCacheApi.set( key, data )
    }
  }

  def set( key: String, data: String ): Unit = {
    asyncCacheApi.set( key, data )
  }

  def get( key: String ): Future[String] = {
    asyncCacheApi.get[String]( key )
      .map( _.getOrElse[String]( "" ) )
  }

}

class RedisCache @Inject() ( system: ActorSystem, @NamedCache( "local" ) local: CacheAsyncApi ) extends CustomCache {

  implicit val ec: ExecutionContext = system.dispatchers.lookup( "contexts.cache-operations" )

  def load( values: Map[String, String] ): Unit = {
    values.foreach {
      case ( key, data ) => local.set( key, data )
    }
  }

  def set( key: String, data: String ): Unit = {
    local.set( key, data )
  }

  def get( key: String ): Future[String] = {
    local.get[String]( key )
      .map( _.getOrElse[String]( "" ) )
  }

}