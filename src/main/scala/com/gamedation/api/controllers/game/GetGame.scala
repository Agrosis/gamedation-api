package com.gamedation.api.controllers.game

import com.gamedation.api.{PayloadError, PayloadSuccess}
import com.gamedation.api.actions.InjectedAction
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class GetGame() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    req.pathVars.get("gameId").map(_.toInt).flatMap(gameId => request.env.games.getGameById(gameId)) match {
      case Some(game) => {
        PayloadSuccess(JsObject(
          "game" -> game.toJson(request.env.games.hasUpvoted(game.id, 1))
        ))
      }
      case None => PayloadError("Couldn't find that game.")
    }
  }

}
