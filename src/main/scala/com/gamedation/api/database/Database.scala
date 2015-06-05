package com.gamedation.api.database

import com.plasmaconduit.jsonconfig.JsonConfig
import com.plasmaconduit.validation.Validation
import org.flywaydb.core.Flyway

import scala.slick.driver.MySQLDriver.simple._

trait Database {

  val config = JsonConfig.loadFromFile("./conf/gamedation.json")

  val db = Database.forURL(
    url      = config.flatMap(_.getString("db.url")).getOrElse(""),
    user     = config.flatMap(_.getString("db.user")).getOrElse(""),
    password = config.flatMap(_.getString("db.password")).getOrElse(""),
    driver   = config.flatMap(_.getString("db.driver")).getOrElse("")
  )

  def database[T](s: Session => T): T = {
    db.withSession { implicit session =>
      s(session)
    }
  }

  def initialize(): Validation[Throwable, Int] = {
    for (
      config <- JsonConfig.loadFromFile("./conf/gamedation.json");
      driver <- config.getString("db.driver");
      url <- config.getString("db.url");
      user <- config.getString("db.user");
      password <- config.getString("db.password")
    ) yield {
      println("Applying Flyway migrations.")

      val flyway = new Flyway()
      flyway.setDataSource(url, user, password)
      flyway.migrate()
    }
  }

  val tables = new Tables()

  class Tables() {

    val comments = TableQuery[Comments]
    val games = TableQuery[Games]
    val gameImages = TableQuery[GameImages]
    val members = TableQuery[Members]
    val upvotes = TableQuery[Upvotes]

  }

}
