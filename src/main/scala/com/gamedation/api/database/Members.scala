package com.gamedation.api.database

import scala.slick.driver.MySQLDriver.simple._

final case class DbMember(id: Long, status: Int, username: String, password: String, email: String, joined: Long)

final case class Members(tag: Tag) extends Table[DbMember](tag, "members") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def status = column[Int]("status")
  def username = column[String]("username")
  def password = column[String]("password")
  def email = column[String]("email")
  def joined = column[Long]("joined")

  def * = (id, status, username, password, email, joined) <> (DbMember.tupled, DbMember.unapply)

}

