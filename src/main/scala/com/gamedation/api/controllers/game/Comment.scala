package com.gamedation.api.controllers.game

import com.gamedation.api.validators.CommentForm
import com.gamedation.api.{PayloadError, PayloadSuccess}
import com.gamedation.api.actions.{GameExists, CheckFeatured, Authenticated, InjectedAction}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.parsers.JsonBodyParser
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject
import com.plasmaconduit.validation.{Failure, Success}

final case class Comment() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Authenticated { member =>
      req.pathVars.get("gameId").map(_.toInt) match {
        case Some(gameId) => {
          CheckFeatured(gameId, request.user) { () =>
            GameExists(gameId) { game =>

              JsonBodyParser(req).mapError(_ => "Comment not found").flatMap(_.as[CommentForm]) match {
                case Success(comment) => {
                  request.env.games.comment(member.id, gameId, req.pathVars.get("parentID").map(_.toLong), comment.text) match {
                    case Some(c) => PayloadSuccess(JsObject("comment" -> c.toJson()))
                    case None => PayloadError("Failed to post comment.")
                  }
                }
                case Failure(f) => PayloadError(f)
              }

            }
          }
        }
        case None => PayloadError("Game id not specified.")
      }
    }
  }

}

