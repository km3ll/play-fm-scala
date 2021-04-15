package me.khazaddum.infrastructure.parser

import me.khazaddum.domain.model.Account
import play.api.libs.json.Json
import play.api.libs.json._

trait JsonParsers {

  implicit val accountFormat: OFormat[Account] = Json.format[Account]

}
