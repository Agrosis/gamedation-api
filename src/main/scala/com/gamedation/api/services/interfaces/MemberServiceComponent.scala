package com.gamedation.api.services.interfaces

import com.gamedation.api.models.Member
import com.plasmaconduit.validation.Validation

trait MemberServiceComponent {

  val members: MemberService

  trait MemberService {

    def getMemberById(id: Long): Option[Member]

    def getMemberByUsername(username: String): Option[Member]

    def getMemberByEmail(email: String): Option[Member]

    def authenticate(email: String, password: String): Option[Member]

    def register(username: String, password: String, email: String): Option[Member]

    def createToken(member: Member): String

    def validateToken(token: String): Validation[String, String]

  }

}
