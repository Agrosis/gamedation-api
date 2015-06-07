package com.gamedation.api.controllers

import com.gamedation.api.actions.{Authenticated, InjectedAction}
import com.gamedation.api.parsers.{SteamLinkParser, GameJoltLinkParser}
import com.gamedation.api.{PayloadError, PayloadSuccess}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.edge.HttpClient
import com.plasmaconduit.framework.parsers.JsonBodyParser
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}
import com.plasmaconduit.framework.mvc.Controller

import com.gamedation.api.Payload._
import com.plasmaconduit.json.JsObject
import com.plasmaconduit.url.{RelativeURL, URL}
import com.plasmaconduit.validation.{Failure, Success}

final case class ProcessLink() extends Controller {

  override def action(implicit req: HttpRequest): Box[Throwable, HttpResponse] = InjectedAction { implicit request =>
    Authenticated { member =>
      req.queryStringParameters.get("url") match {
        case Some(url) => {
          // now run the url through all the parsers
          val gamejolt = GameJoltLinkParser(url)
          val steam = SteamLinkParser(url)

          gamejolt match {
            case Success(gjLink) => {
              HttpClient.get(URL(s"http://localhost:1340/gamejolt/${gjLink.id}").getOrElse(new URL("", "", None, RelativeURL("", None))))
                .mapError(_.throwable)
                .flatMap(response => {
                response.getContentAsJson match {
                  case Success(data) => {
                    PayloadSuccess(JsObject(
                      "type" -> "gamejolt",
                      "gjData" -> data
                    ))
                  }
                  case Failure(_) => PayloadError("Invalid gamejolt game.")
                }
              })
            }
            case Failure(_) => {
              steam match {
                case Success(steamLink) => {
                  HttpClient.get(URL(s"http://localhost:1340/steam/${steamLink.id}").getOrElse(new URL("", "", None, RelativeURL("", None))))
                    .mapError(_.throwable)
                    .flatMap(response => {
                    response.getContentAsJson match {
                      case Success(data) => {
                        PayloadSuccess(JsObject(
                          "type" -> "steam",
                          "steamData" -> data
                        ))
                      }
                      case Failure(_) => PayloadError("Invalid steam game.")
                    }
                  })
                }
                case Failure(_) => {
                  PayloadSuccess(JsObject(
                    "type" -> "other"
                  ))
                }
              }
            }
          }
        }
        case None => PayloadError("No URL was provided.")
      }
    }
  }

}
