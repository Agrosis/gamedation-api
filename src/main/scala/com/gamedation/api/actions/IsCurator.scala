package com.gamedation.api.actions

import com.gamedation.api.PayloadError
import com.gamedation.api.actions.InjectedAction.InjectedRequest
import com.gamedation.api.models.{Normal, Guest, Member}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.HttpResponse

object IsCurator {

  def apply(action: Member => Box[Throwable, HttpResponse])(implicit request: InjectedRequest): Box[Throwable, HttpResponse] = Authenticated { m =>
    m.status match {
      case Normal => PayloadError("You are not allowed to do that.")
      case _ => action(m)
    }
  }

}
