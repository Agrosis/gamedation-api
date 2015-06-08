package com.gamedation.api.database

import scala.slick.driver.MySQLDriver.simple._

final case class DbFeaturedGame(id: Long, game: Long, time: Long)

final case class FeaturedGames(tag: Tag) extends Table[DbFeaturedGame](tag, "featured_games") with Database {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def gameId = column[Long]("gameId") // foreign key to games
  def time = column[Long]("time")

  def * = (id, gameId, time) <> (DbFeaturedGame.tupled, DbFeaturedGame.unapply)

  def game = foreignKey("game_fk", gameId, tables.games)(_.id)

}
