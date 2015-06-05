package com.gamedation.api.database

import com.plasmaconduit.jsonconfig.JsonConfig
import com.plasmaconduit.validation.Validation
import org.flywaydb.core.Flyway

import scala.slick.driver.MySQLDriver.simple._

trait Database {
  

  def applyMigrations(): Validation[Throwable, Int] = {
    for (
      config <- JsonConfig.loadFromFile("./conf/gamedation.json");
      url <- config.getString("db.url");
      user <- config.getString("db.user");
      password <- config.getString("db.password")
    ) yield {
      println("Flyway migrations applied successfully.")

      val flyway = new Flyway()
      flyway.setDataSource(url, user, password)
      flyway.migrate()
    }
  }

  val tables = new Tables()

  class Tables() {

    val comments = TableQuery[Comments]
    val games = TableQuery[Games]
    val members = TableQuery[Members]
    val upvotes = TableQuery[Upvotes]

  }

}
