package com.gamedation.api.database

import scala.slick.driver.MySQLDriver.simple._

final case class DbUpvote(member: Long, game: Long, time: Long)

final case class Upvotes(tag: Tag) extends Table[DbUpvote](tag, "upvotes") with Database {

  def memberId = column[Long]("memberId") // foreign to members
  def gameId = column[Long]("gameId") // foreign to games
  def time = column[Long]("time")

  def * = (memberId, gameId, time) <> (DbUpvote.tupled, DbUpvote.unapply)

  def member = foreignKey("member_fk", memberId, tables.members)(_.id)
  def game = foreignKey("game_fk", gameId, tables.games)(_.id)

}
