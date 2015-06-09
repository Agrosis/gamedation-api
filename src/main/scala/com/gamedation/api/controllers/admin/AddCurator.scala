package com.gamedation.api.controllers.game

import com.gamedation.api.{PayloadError, PayloadSuccess}
import com.gamedation.api.actions.{IsAdmin, InjectedAction}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class AddCurator() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    IsAdmin { admin =>
      req.pathVars.get("username") match {
        case Some(username) => {
          PayloadSuccess(JsObject(
            "result" -> request.env.members.makeCurator(username)
          ))
        }
        case None => PayloadError("Username not specified.")
      }
    }
  }

}
