package me.khazaddum.infrastructure.persistence

import slick.lifted.TableQuery

package object tables {

  val tblAccounts = TableQuery[AccountTable]

}
