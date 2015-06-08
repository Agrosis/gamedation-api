package com.gamedation.api.controllers.game

import com.gamedation.api.models.{Other, Steam, GameJolt, GameSite}
import com.gamedation.api.parsers.{SteamLinkParser, GameJoltLinkParser}
import com.gamedation.api.{PayloadSuccess, PayloadError}
import com.gamedation.api.actions.{Authenticated, InjectedAction}
import com.gamedation.api.validators.SubmitGameForm
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.mvc.Controller
import com.plasmaconduit.framework.parsers.JsonBodyParser
import com.plasmaconduit.framework.{HttpRequest, HttpResponse}
import com.plasmaconduit.json.JsObject
import com.plasmaconduit.validation.{Failure, Success}

final case class Submit() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Authenticated { member =>
      if(request.env.games.canUploadGame(member)) {
        JsonBodyParser(req).flatMap(_.as[SubmitGameForm]) match {
          case Success(s: SubmitGameForm) => {
            val a = request.env.games.createGame(s.link, s.name, s.description, s.windows, s.mac, s.linux, s.browser, s.iOS, s.android, s.images, member.id, getSite(s.link))

            a match {
              case Some(game) => PayloadSuccess(JsObject(
                "game" -> game.toJson()
              ))
              case None => PayloadError("Unable to create game.")
            }
          }
          case Failure(_) => PayloadError("Invalid json form data.")
        }
      } else {
        PayloadError("You can't post anymore games.")
      }
    }
  }

  private def getSite(link: String): GameSite = {
    val gamejolt = GameJoltLinkParser(link)
    val steam = SteamLinkParser(link)

    if(gamejolt.isSuccess) GameJolt
    else if(steam.isSuccess) Steam
    else Other
  }

}
