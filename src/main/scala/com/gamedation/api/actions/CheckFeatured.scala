package com.gamedation.api.actions

import com.gamedation.api.PayloadError
import com.gamedation.api.actions.InjectedAction.InjectedRequest
import com.gamedation.api.models.{User, Guest, Member}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.HttpResponse

object CheckFeatured {

  def apply(gameId: Long, user: User)(action: () => Box[Throwable, HttpResponse])(implicit request: InjectedRequest): Box[Throwable, HttpResponse] = {
    if(!request.env.games.isFeatured(gameId)) {
      user match {
        case m: Member => {
          if(m.status.isCurator()) {
            action()
          } else {
            PayloadError("Unable to view that game.")
          }
        }
        case Guest => PayloadError("Unable to view that game.")
      }
    } else {
      action()
    }
  }

}
