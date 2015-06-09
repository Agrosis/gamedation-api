package com.gamedation.api.actions

import com.gamedation.api.PayloadError
import com.gamedation.api.actions.InjectedAction.InjectedRequest
import com.gamedation.api.models.{Admin, Member}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.HttpResponse

object IsAdmin {

  def apply(action: Member => Box[Throwable, HttpResponse])(implicit request: InjectedRequest): Box[Throwable, HttpResponse] = Authenticated { m =>
    m.status match {
      case Admin => action(m)
      case _ => PayloadError("You are not allowed to do that.")
    }
  }

}
