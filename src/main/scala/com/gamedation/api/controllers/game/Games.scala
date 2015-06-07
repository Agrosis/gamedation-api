package com.gamedation.api.controllers.game

import com.gamedation.api.PayloadSuccess
import com.gamedation.api.actions.InjectedAction
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.framework.{HttpRequest, HttpResponse}
import com.plasmaconduit.json.JsObject

final case class Games() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    PayloadSuccess(JsObject(
      "games" -> request.env.games.getGamesFor(1).map(g => g.toJson(request.env.games.hasUpvoted(g.id, 1)))
    ))
  }

}
