package com.gamedation.api.controllers.user

import com.gamedation.api.{PayloadError, PayloadSuccess}
import com.gamedation.api.actions.{Unauthenticated, InjectedAction}
import com.gamedation.api.validators.LogInForm
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.parsers.JsonBodyParser
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject
import com.plasmaconduit.validation.{Success, Failure}

final case class LogIn() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Unauthenticated { () =>
      JsonBodyParser(req).mapError(_.message).flatMap(_.as[LogInForm]) match {
        case Success(logIn) => {
          request.env.members.authenticate(logIn.email, logIn.password) match {
            case Some(m) => PayloadSuccess(JsObject(
              "user" -> m.toJson(),
              "token" -> request.env.members.createToken(m)
            ))
            case _ => PayloadError("Invalid e-mail or password.")
          }
        }
        case Failure(f) => PayloadError(f)
      }
    }
  }

}
