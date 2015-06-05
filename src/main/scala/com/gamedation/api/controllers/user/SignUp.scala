package com.gamedation.api.controllers.user

import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller

final case class SignUp() extends Controller {

  override def action(implicit request: HttpRequest): Box[Throwable, HttpResponse] = ???

}
