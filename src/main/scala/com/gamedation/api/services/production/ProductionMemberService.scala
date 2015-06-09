package com.gamedation.api.services.production

import com.gamedation.api.database.DbMember
import com.gamedation.api.models._
import com.gamedation.api.services.interfaces.MemberServiceComponent
import com.gamedation.api.validators.WebToken
import com.plasmaconduit.json.JsObject
import com.plasmaconduit.jwa.JWTSha512
import com.plasmaconduit.jwt.JSONWebToken
import com.plasmaconduit.validation.Validation
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

    override def getCurators(): List[Member] = database { implicit session =>
      val q = for {
        u <- tables.members if u.status === Curator.toInt
      } yield u

      q.list.map(u => {
        Member(u.id, Status.fromInt(u.status), u.username, u.email)
      })
    }

    override def makeCurator(username: String): Boolean = database { implicit session =>
      val q = for {
        u <- tables.members if u.username === username && u.status === Normal.toInt
      } yield u.status

      q.update(Curator.toInt) > 0
    }

    override def authenticate(email: String, password: String): Option[Member] = database { implicit session =>
      val q = for {
        u <- tables.members if u.email === email
      } yield u

      q.firstOption.map(x => BCrypt.checkpw(password, x.password)) match {
        case Some(true) => getMemberByEmail(email)
        case _ => None
      }
    }

    override def register(username: String, password: String, email: String): Option[Member] = database { implicit session =>
      if(getMemberByUsername(username).isEmpty && getMemberByEmail(email).isEmpty) {
        val status: Status = getMemberById(1).map(_ => Normal).getOrElse(Admin)
        val id = (tables.members returning tables.members.map(_.id)) += DbMember(0, status.toInt, username, BCrypt.hashpw(password, "$2a$10$tD5ezWLH0hRdvYzZeL5epu"), email, System.currentTimeMillis())

        getMemberById(id)
      } else {
        None
      }
    }

    override def createToken(member: Member): String = {
      JSONWebToken.sign(JWTSha512, "JWT_TOKEN_HASH".getBytes("UTF-8"), JsObject("email" -> member.email))
    }

    override def validateToken(token: String): Validation[String, String] = {
      JSONWebToken.verify("JWT_TOKEN_HASH".getBytes("UTF-8"), token).mapError(_ => "Invalid token.").flatMap(_.as[WebToken]).map(_.email)
    }

  }

}
