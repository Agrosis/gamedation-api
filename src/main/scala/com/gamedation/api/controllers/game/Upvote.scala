package com.gamedation.api.controllers.game

import com.gamedation.api.PayloadSuccess
import com.gamedation.api.actions.{InjectedAction, Authenticated}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class Upvote() extends Controller {

  override def action(implicit request: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Authenticated { member =>
      PayloadSuccess(JsObject(
        "points" -> 32
      ))
    }
  }

}
