package me

import java.util.{ Calendar, Date }
import org.joda.time.DateTime
import org.scalacheck.Gen

object BaseGenerators {

  val GenAlpha01: Gen[String] = Gen.alphaStr.map( _.take( 1 ).toUpperCase )

  val GenApellido: Gen[String] = Gen.alphaStr.map( _.take( 50 ) )

  val GenBigDecimal: Gen[BigDecimal] = Gen.choose[Double]( 0, 10000000 ).map( BigDecimal( _ ) )

  val GenDescripcion: Gen[String] = Gen.alphaStr.map( _.take( 100 ) )

  val GenCelular: Gen[String] = Gen.choose[Long]( 300000L, 320000L ).map( _.toString )

  val GenCodigo: Gen[String] = Gen.choose( 0, 99999 ).map( value => "%05d".format( value ) )

  val GenNombre: Gen[String] = Gen.alphaStr.map( _.take( 50 ) )

  val GenEmail: Gen[String] = for {
    nombre <- GenNombre
    dominio <- Gen.oneOf( "gmail.com", "hotmail.com", "yahoo.com" )
  } yield nombre.trim + "@" + dominio

  val GenDNI: Gen[String] = for {
    alpha <- GenAlpha01
    number <- GenCodigo
  } yield alpha + number

  val GenDouble: Gen[Double] = Gen.choose[Double]( 0, 10000000 )

  val GenLong: Gen[Long] = Gen.choose[Long]( 0L, 10000000L )

  val GenNickname: Gen[String] = Gen.alphaStr.map( _.take( 20 ) )

  val GenNumericString: Gen[String] = Gen.numStr.map( _.take( 10 ) )

  val GenPorcentaje: Gen[Double] = Gen.choose[Double]( 0, 100 )

  val GenSexo: Gen[String] = Gen.oneOf( "M", "F" )

  val GenSoN: Gen[String] = Gen.oneOf( "S", "N" )

  val GenBoolean: Gen[Boolean] = Gen.oneOf( true, false )

  def addDays( n: Int ): Calendar = {
    val date = new DateTime( 1990, 1, 1, 0, 0, 0, 0 )
    val calendar = Calendar.getInstance()
    calendar.setTime( date.toDate )
    calendar.add( Calendar.DAY_OF_YEAR, n )
    calendar
  }

  val GenTimeInMillis: Gen[Long] = for {
    days <- Gen.choose[Int]( 1, 10000 )
  } yield addDays( days ).getTimeInMillis

  val GenDate: Gen[Date] = for {
    days <- Gen.choose[Int]( 1, 10000 )
  } yield addDays( days ).getTime

  val GenDateTime: Gen[DateTime] = for {
    days <- Gen.choose[Int]( 1, 10000 )
  } yield new DateTime( addDays( days ).getTimeInMillis )

  val GenUUID: Gen[String] = Gen.uuid.map( _.toString )

}
