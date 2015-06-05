package com.gamedation.api.database

import scala.slick.driver.MySQLDriver.simple._

final case class DbGame(id: Long, link: String, name: String, description: String, windows: Boolean, mac: Boolean, linux: Boolean, browser: Boolean, iOS: Boolean, android: Boolean, points: Int, poster: Long, site: Int, submitted: Long)

final case class Games(tag: Tag) extends Table[DbGame](tag, "games") with Database {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def link = column[String]("link", O.DBType("TEXT"))
  def name = column[String]("name")
  def description = column[String]("description")
  def windows = column[Boolean]("windows")
  def mac = column[Boolean]("mac")
  def linux = column[Boolean]("linux")
  def browser = column[Boolean]("browser")
  def iOS = column[Boolean]("iOS")
  def android = column[Boolean]("android")
  def points = column[Int]("points")
  def posterId = column[Long]("posterId") // foreign key to users
  def site = column[Int]("site")
  def submitted = column[Long]("submitted")

  def * = (id, link, name, description, windows, mac, linux, browser, iOS, android, points, posterId, site, submitted) <> (DbGame.tupled, DbGame.unapply)

  def poster = foreignKey("poster_fk", posterId, tables.members)(_.id)

}
