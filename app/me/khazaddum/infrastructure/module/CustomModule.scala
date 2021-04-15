package me.khazaddum.infrastructure.module

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import me.khazaddum.domain.repository.AccountRepository
import me.khazaddum.infrastructure.module.playfm._
import me.khazaddum.infrastructure.persistence.{ AccountRepositoryH2, AccountRepositoryPostgres }
import play.api.Logger.logger
import play.api.{ ApplicationLoader, Logger }
import play.api.inject.guice.{ GuiceApplicationBuilder, GuiceApplicationLoader }

class CustomModule extends AbstractModule {

  override def configure(): Unit = {

    // H2 Repository
    bind( classOf[AccountRepository] ).to( classOf[AccountRepositoryH2] ).asEagerSingleton()

    // Postgres Repository
    // bind( classOf[AccountRepository] ).to( classOf[AccountRepositoryPostgres] ).asEagerSingleton()

    // Custom StopHook
    bind( classOf[CustomStopHook] ).to( classOf[ConnectionHook] ).asEagerSingleton()

    // Custom CacheApi
    bind( classOf[CustomCache] ).to( classOf[PlayFrameworkCache] ).asEagerSingleton()
    //bind( classOf[CustomCacheApi] ).to( classOf[RedisCache] ).asEagerSingleton()

    // Retrier
    bind( classOf[Retrier] ).annotatedWith( Names.named( "retry-actors" ) ).to( classOf[RetrierWithActorSystem] ).asEagerSingleton()
    bind( classOf[Retrier] ).annotatedWith( Names.named( "retry-tasks" ) ).to( classOf[RetrierWithTasks] ).asEagerSingleton()

  }

}