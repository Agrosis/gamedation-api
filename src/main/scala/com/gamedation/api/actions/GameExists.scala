package com.gamedation.api.actions

import com.gamedation.api.PayloadError
import com.gamedation.api.actions.InjectedAction.InjectedRequest
import com.gamedation.api.models.Game
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.HttpResponse

object GameExists {

  def apply(gameId: Long)(action: Game => Box[Throwable, HttpResponse])(implicit request: InjectedRequest): Box[Throwable, HttpResponse] = {
    request.env.games.getGameById(gameId) match {
      case Some(game) => action(game)
      case None => PayloadError("That app doesn't exist")
    }
  }
  
}
