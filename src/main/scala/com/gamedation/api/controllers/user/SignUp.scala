package com.gamedation.api.controllers.user

import com.gamedation.api.{PayloadError, PayloadSuccess}
import com.gamedation.api.actions.{Unauthenticated, InjectedAction}
import com.gamedation.api.validators.SignUpForm
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.parsers.JsonBodyParser
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.json.JsObject
import com.plasmaconduit.validation.{Success, Failure}

final case class SignUp() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Unauthenticated { () =>
      JsonBodyParser(req).mapError(e => e.message).flatMap(_.as[SignUpForm]) match {
        case Success(form) => {
          request.env.members.register(form.username, form.password, form.email) match {
            case Some(member) => {
              PayloadSuccess(JsObject(
                "member" -> member.toJson()
              ), Map("token" -> request.env.members.createToken(member)))
            }
            case None => PayloadError("A member with that username or e-mail already exists.")
          }
        }
        case Failure(f) => PayloadError(f)
      }
    }
  }

}
