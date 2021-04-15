package me.khazaddum.domain.service

import cats.data.Reader
import cats.syntax.either._
import me.khazaddum.domain.model.Account
import me.khazaddum.domain.repository.AccountRepository
import play.api.mvc.{ Action, AnyContent }

import scala.concurrent.{ ExecutionContext, Future }

object AccountService {

  def findAccount( no: String )( implicit ec: ExecutionContext ): Reader[AccountRepository, Future[Either[String, Account]]] = Reader {
    repository =>
      repository.find( no ).map {
        case None        => s"Account no [$no] not found".asLeft
        case Some( acc ) => acc.asRight
      }

  }

}
