package com.gamedation.api.services.production

import com.gamedation.api.database.DbMember
import com.gamedation.api.models.{Status, Admin, Normal, Member}
import com.gamedation.api.services.interfaces.MemberServiceComponent
import org.mindrot.jbcrypt.BCrypt

import scala.slick.driver.MySQLDriver.simple._

trait ProductionMemberService extends MemberServiceComponent { this: ProductionServices =>

  val members = new MemberServiceImpl

  final class MemberServiceImpl extends MemberService {

    override def getMemberById(id: Long): Option[Member] = database { implicit session =>
      val q = for {
        u <- tables.members if u.id === id
      } yield u
      q.firstOption.map(u => {
        Member(u.id, Status.fromInt(u.status), u.username, u.email)
      })
    }

    override def getMemberByUsername(username: String): Option[Member] = database { implicit session =>
      val q = for {
        u <- tables.members if u.username === username
      } yield u
      q.firstOption.map(u => {
        Member(u.id, Status.fromInt(u.status), u.username, u.email)
      })
    }

    override def getMemberByEmail(email: String): Option[Member] = database { implicit session =>
      val q = for {
        u <- tables.members if u.email === email
      } yield u
      q.firstOption.map(u => {
        Member(u.id, Status.fromInt(u.status), u.username, u.email)
      })
    }

    override def authenticate(email: String, password: String): Boolean = database { implicit session =>
      val q = for {
        u <- tables.members if u.email === email
      } yield u

      q.firstOption.map(x => BCrypt.checkpw(password, x.password)).getOrElse(false)
    }

    override def register(username: String, password: String, email: String): Option[Member] = database { implicit session =>
      if(getMemberByUsername(username).isEmpty && getMemberByEmail(email).isEmpty) {
        val status: Status = getMemberById(1).map(_ => Admin).getOrElse(Normal)
        val id = (tables.members returning tables.members.map(_.id)) += DbMember(0, status.toInt, username, BCrypt.hashpw(password, "SALT"), email, System.currentTimeMillis())

        getMemberById(id)
      } else {
        None
      }
    }
  }

}
