package com.gamedation.api.actions

import com.gamedation.api.PayloadError
import com.gamedation.api.actions.InjectedAction.InjectedRequest
import com.gamedation.api.models.Guest
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.HttpResponse

object Unauthenticated {

  def apply(action: () => Box[Throwable, HttpResponse])(implicit request: InjectedRequest): Box[Throwable, HttpResponse] = {
    request.user match {
      case Guest => action()
      case _ => PayloadError("You are not allowed to do this.")
    }
  }

}
