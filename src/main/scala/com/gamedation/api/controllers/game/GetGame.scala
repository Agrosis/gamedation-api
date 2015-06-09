package com.gamedation.api.controllers.game

import com.gamedation.api.models.{Guest, Member}
import com.gamedation.api.{PayloadError, PayloadSuccess}
import com.gamedation.api.actions.{CheckFeatured, InjectedAction}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class GetGame() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    req.pathVars.get("gameId").map(_.toInt).flatMap(gameId => request.env.games.getGameById(gameId)) match {
      case Some(game) => {
        CheckFeatured(game.id, request.user) { () =>
          val comments = request.env.games.getComments(game.id).map(_.toJson())
          request.user match {
            case m: Member => {
              PayloadSuccess(JsObject(
                "game" -> game.toJson(request.env.games.hasUpvoted(game.id, m.id)),
                "upvoters" -> request.env.games.getUpvoters(game.id).flatMap(id => request.env.members.getMemberById(id)).map(_.avatar(64)),
                "comments" -> comments
              ))
            }
            case Guest => {
              PayloadSuccess(JsObject(
                "game" -> game.toJson(),
                "upvoters" -> request.env.games.getUpvoters(game.id).flatMap(id => request.env.members.getMemberById(id)).map(_.avatar(64)),
                "comments" -> comments
              ))
            }
          }
        }
      }
      case None => PayloadError("Couldn't find that game.")
    }
  }

}
