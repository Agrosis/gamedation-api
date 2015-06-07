package com.gamedation.api.controllers

import com.gamedation.api.actions.InjectedAction
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.edge.http.Ok
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject

final case class Index() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Ok(JsObject(
      "name" -> "api",
      "version" -> "1.0.0"
    ))
  }

}
