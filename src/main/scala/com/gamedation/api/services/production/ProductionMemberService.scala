package com.gamedation.api.services.production

import com.gamedation.api.models.Member
import com.gamedation.api.services.interfaces.MemberServiceComponent

trait ProductionMemberService extends MemberServiceComponent {

  val members = new MemberServiceImpl

  final class MemberServiceImpl extends MemberService {

    override def getMemberById(id: Long): Option[Member] = ???

  }

}
