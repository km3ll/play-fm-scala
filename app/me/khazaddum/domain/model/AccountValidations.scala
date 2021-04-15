package me.khazaddum.domain.model

import java.util.Date

import cats.data.ValidatedNel
import cats.syntax.option._
import cats.syntax.validated._

object AccountValidations {

  type Validation[A] = ValidatedNel[String, A]

  def validateAccountNo( no: String ): Validation[String] = {
    if ( no.isEmpty || no.length != 11 ) s"Account number must be 11 characters long: found $no".invalidNel
    else no.validNel
  }

  def validateOpenCloseDate( openDate: Date, closeDate: Option[Date] ): Validation[( Date, Option[Date] )] = {
    closeDate.map { closeD =>
      if ( closeD before openDate ) s"Close date [$closeD] cannot be earlier than open date [$openDate]".invalidNel
      else ( openDate, closeD.some ).validNel
    }.getOrElse( ( openDate, closeDate ).validNel )
  }

  def validateRate( rate: Double ): Validation[Double] = {
    if ( rate <= 0 ) s"Interest rate must be greater than 0: found $rate".invalidNel
    else rate.validNel
  }

  def validateAccountAlreadyClosed( account: Account ): Validation[Account] = {
    if ( account.dateOfClose.isDefined ) s"Account ${account.no} is already closed".invalidNel
    else account.validNel
  }

  def validateCloseDate( account: Account, closeDate: Date ): Validation[Date] = {
    if ( closeDate before account.dateOfOpen ) s"Close date [$closeDate] cannot be earlier than open date [${account.dateOfOpen}]".invalidNel
    else closeDate.validNel
  }

  def checkBalance( account: Account, amount: BigDecimal ): Validation[Account] = {
    if ( amount > account.balance ) s"Insufficient amount in ${account.no} to debit".invalidNel
    else account.validNel
  }

}
