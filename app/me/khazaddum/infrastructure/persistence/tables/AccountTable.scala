package me.khazaddum.infrastructure.persistence.tables

import java.sql.Date
import slick.jdbc.PostgresProfile.api._

class AccountTable( tag: Tag ) extends Table[AccountRecord]( tag, "tbl_bank_account" ) {

  def no = column[String]( "no", O.PrimaryKey )
  def openDate = column[Date]( "open_date" )
  def closeDate = column[Option[Date]]( "close_date" )
  def balance = column[BigDecimal]( "balance" )

  def * = ( no, openDate, closeDate, balance ) <> ( ( AccountRecord.apply _ ).tupled, AccountRecord.unapply )

}

case class AccountRecord(
  no:        String,
  openDate:  Date,
  closeDate: Option[Date],
  balance:   BigDecimal
)