package me.khazaddum.infrastructure.persistence

import akka.actor.ActorSystem
import javax.inject.Inject
import me.khazaddum.domain.model.Account
import me.khazaddum.domain.repository.AccountRepository
import me.khazaddum.infrastructure.persistence.tables.tblAccounts
import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile

import scala.concurrent.{ ExecutionContext, Future }

class AccountRepositoryPostgres @Inject() ( @NamedDatabase( "postgres" ) val dbConfigProvider:DatabaseConfigProvider, system: ActorSystem ) extends AccountRepository {

  implicit val ec: ExecutionContext = system.dispatchers.lookup( "contexts.db-operations" )

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  def find( accountNo: String ): Future[Option[Account]] = {

    val query = tblAccounts.filter( _.no === accountNo )
    val action = query.result.headOption

    db.run( action ).map( _.map( rec => Account( rec.no, rec.openDate, rec.closeDate, rec.balance ) ) )

  }

}