package com.gamedation.api.controllers

import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller

final case class Submit() extends Controller {

  override def action(implicit request: HttpRequest): Box[Throwable, HttpResponse] = ???

}
