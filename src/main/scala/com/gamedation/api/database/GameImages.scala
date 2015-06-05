package com.gamedation.api.database

import scala.slick.driver.MySQLDriver.simple._

final case class DbGameImage(id: Long, game: Long, link: String)

final case class GameImages(tag: Tag) extends Table[DbGameImage](tag, "game_images") with Database {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def gameId = column[Long]("gameId") // foreign key to games
  def link = column[String]("link", O.DBType("TEXT"))

  def * = (id, gameId, link) <> (DbGameImage.tupled, DbGameImage.unapply)

  def game = foreignKey("game_fk", gameId, tables.games)(_.id)

}
