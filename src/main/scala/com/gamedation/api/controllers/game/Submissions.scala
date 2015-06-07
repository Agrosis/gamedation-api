package com.gamedation.api.controllers

import com.gamedation.api.PayloadSuccess
import com.gamedation.api.actions.{IsCurator, InjectedAction}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class Submissions() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    IsCurator { c =>
      PayloadSuccess(JsObject(
        "games" -> request.env.games.getLatestGames.map(g => g.toJson(request.env.games.hasUpvoted(g.id, 1)))
      ))
    }
  }

}
