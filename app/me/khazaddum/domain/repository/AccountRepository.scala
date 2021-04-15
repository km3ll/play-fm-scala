package me.khazaddum.domain.repository

import me.khazaddum.domain.model.Account
import scala.concurrent.Future

trait AccountRepository {

  def find( no: String ): Future[Option[Account]]

}
