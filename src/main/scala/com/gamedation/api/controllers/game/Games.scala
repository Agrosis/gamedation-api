package com.gamedation.api.controllers.game

import java.util.{Calendar, GregorianCalendar}

import com.gamedation.api.PayloadSuccess
import com.gamedation.api.actions.InjectedAction
import com.gamedation.api.models.{Guest, Member}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.framework.{HttpRequest, HttpResponse}
import com.plasmaconduit.json.JsObject

final case class Games() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    val date = new GregorianCalendar()
    date.set(Calendar.HOUR_OF_DAY, 0)
    date.set(Calendar.MINUTE, 0)
    date.set(Calendar.SECOND, 0)
    date.set(Calendar.MILLISECOND, 0)

    val time = date.getTimeInMillis
    val t2 = time - 86400000
    val t3 = time - 86400000

    request.user match {
      case m: Member => {
        PayloadSuccess(JsObject(
          "games" -> request.env.games.getFeatured(time).map(g => g.toJson(request.env.games.hasUpvoted(g.id, m.id)))
        ))
      }
      case Guest => {
        PayloadSuccess(JsObject(
          "games" -> request.env.games.getFeatured(time).map(g => g.toJson())
        ))
      }
    }
  }

}
