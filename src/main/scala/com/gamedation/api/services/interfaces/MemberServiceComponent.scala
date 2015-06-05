package com.gamedation.api.services.interfaces

import com.gamedation.api.models.Member

trait MemberServiceComponent {

  val members: MemberService

  trait MemberService {

    def getMemberById(id: Long): Option[Member]

  }

}
