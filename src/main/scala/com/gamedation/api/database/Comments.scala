package com.gamedation.api.database

import scala.slick.driver.MySQLDriver.simple._

final case class DbComment(id: Long, game: Long, member: Long, parent: Option[Long], text: String, time: Long)

final case class Comments(tag: Tag) extends Table[DbComment](tag, "comments") with Database {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def gameId = column[Long]("gameId")
  def memberId = column[Long]("memberId")
  def parentId = column[Long]("parentId", O.Nullable)
  def text = column[String]("text", O.DBType("TEXT"))
  def time = column[Long]("time")

  def * = (id, gameId, memberId, parentId.?, text, time) <> (DbComment.tupled, DbComment.unapply)

  def game = foreignKey("game_fk", gameId, tables.games)(_.id)
  def member = foreignKey("member_fk", memberId, tables.members)(_.id)
  def parent = foreignKey("parent_fk", parentId, tables.comments)(_.id)

}

