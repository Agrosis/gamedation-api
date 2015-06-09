package com.gamedation.api.controllers

import com.gamedation.api.PayloadSuccess
import com.gamedation.api.actions.{IsAdmin, InjectedAction}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class AdminData() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    IsAdmin { m =>
      PayloadSuccess(JsObject(
        "curators" -> request.env.members.getCurators().map(_.username)
      ))
    }
  }

}
