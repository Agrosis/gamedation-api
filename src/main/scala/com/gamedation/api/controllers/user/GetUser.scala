package com.gamedation.api.controllers.user

import com.gamedation.api.PayloadSuccess
import com.gamedation.api.actions.{Authenticated, InjectedAction}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class GetUser() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Authenticated { member =>
      PayloadSuccess(JsObject("user" -> member.toJson()))
    }
  }

}
