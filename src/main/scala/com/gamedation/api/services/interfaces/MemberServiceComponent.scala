package com.gamedation.api.services.interfaces

import com.gamedation.api.models.Member

trait MemberServiceComponent {

  val members: MemberService

  trait MemberService {

    def getMemberById(id: Long): Option[Member]

    def getMemberByUsername(username: String): Option[Member]

    def getMemberByEmail(email: String): Option[Member]

    def authenticate(email: String, password: String): Boolean

    def register(username: String, password: String, email: String): Option[Member]

  }

}
