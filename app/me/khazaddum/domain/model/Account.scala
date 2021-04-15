package me.khazaddum.domain.model

import java.util.{ Calendar, Date }
import cats.Applicative
import cats.implicits._
import AccountValidations._

case class Account( no: String, dateOfOpen: Date, dateOfClose: Option[Date], balance: BigDecimal )

object Account {

  def createAccount( accountNo: String, openDate: Option[Date], closeDate: Option[Date], balance: BigDecimal ): Either[String, Account] = {

    val opDate = openDate.getOrElse( Calendar.getInstance.getTime )

    Applicative[Validation].map2(
      validateAccountNo( accountNo ),
      validateOpenCloseDate( opDate, closeDate )
    ) { ( no, dates ) => Account( no, dates._1, dates._2, balance ) }
      .toEither
      .leftMap( errors => s"Savings Account cannot be created [ ${errors.toList.mkString( "-" )} ]" )

  }

}