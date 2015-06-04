package com.gamedation.api.actions

import com.gamedation.api.PayloadError
import com.gamedation.api.actions.InjectedAction.InjectedRequest
import com.gamedation.api.models.{Guest, Member}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.HttpResponse

object Authenticated {

  def apply(action: Member => Box[Throwable, HttpResponse])(implicit request: InjectedRequest): Box[Throwable, HttpResponse] = {
    request.user match {
      case m: Member => action(m)
      case Guest => PayloadError("You are not allowed to do this.")
    }
  }

}
