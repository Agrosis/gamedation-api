package com.gamedation.api.database

import scala.slick.driver.MySQLDriver.simple._

trait Database {

  val tables = new Tables()

  class Tables() {

    val comments = TableQuery[Comments]
    val games = TableQuery[Games]
    val members = TableQuery[Members]
    val upvotes = TableQuery[Upvotes]

  }

}
