package com.gamedation.api.controllers.game

import com.gamedation.api.{PayloadError, PayloadSuccess}
import com.gamedation.api.actions.{GameExists, InjectedAction, Authenticated}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class Upvote() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Authenticated { member =>
      req.pathVars.get("gameId").map(_.toInt) match {
        case Some(gameId) => {
          GameExists(gameId) { game =>
            PayloadSuccess(JsObject(
              "points" -> request.env.games.voteGame(gameId, member.id).getOrElse(-1)
            ))
          }
        }
        case None => PayloadError("Game id not specified.")
      }
    }
  }

}
