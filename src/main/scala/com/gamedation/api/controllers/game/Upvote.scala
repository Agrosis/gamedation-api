package com.gamedation.api.controllers.game

import com.gamedation.api.{PayloadError, PayloadSuccess}
import com.gamedation.api.actions.{CheckFeatured, GameExists, InjectedAction, Authenticated}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class Upvote() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Authenticated { member =>
      req.pathVars.get("gameId").map(_.toInt) match {
        case Some(gameId) => {
          CheckFeatured(gameId, request.user) { () =>
            GameExists(gameId) { game =>
              val points = request.env.games.voteGame(gameId, member.id).getOrElse(-1)

              if(points >= 5) {
                request.env.games.featureGame(gameId)
              }

              PayloadSuccess(JsObject(
                "points" -> points
              ))
            }
          }
        }
        case None => PayloadError("Game id not specified.")
      }
    }
  }

}
