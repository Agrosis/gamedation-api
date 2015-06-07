package com.gamedation.api

import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.edge.http.{HttpServerResponse, Ok}
import com.plasmaconduit.framework.{HttpSession, HttpResponse}
import com.plasmaconduit.json.{JsNull, JsObject}

import scala.collection.immutable.HashMap

sealed trait Payload

final case class PayloadSuccess(data: JsObject, session: Map[String, String] = HashMap()) extends Payload

final case class PayloadError(message: String, session: Map[String, String] = HashMap()) extends Payload

object Payload {

  private def fromServerResponse(r: HttpServerResponse): HttpResponse = {
    HttpResponse(r.body, r.status, r.headers)
  }

  implicit def payload2HttpResponse(payload: Payload): Box[Throwable, HttpResponse] = payload match {
    case PayloadSuccess(data, session) => {
      fromServerResponse(Ok(JsObject(
        "status" -> 200,
        "data" -> data,
        "error" -> JsNull
      ))).withSession(HttpSession(session))
    }
    case PayloadError(e, session) => {
      fromServerResponse(Ok(JsObject(
        "status" -> 404,
        "data" -> JsNull,
        "error" -> e
      ))).withSession(HttpSession(session))
    }
  }

}

