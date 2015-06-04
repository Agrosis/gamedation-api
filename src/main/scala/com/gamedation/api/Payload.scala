package com.gamedation.api

import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.edge.http.Ok
import com.plasmaconduit.framework.HttpResponse
import com.plasmaconduit.json.{JsNull, JsObject}

sealed trait Payload

final case class PayloadSuccess(data: JsObject) extends Payload

final case class PayloadError(message: String) extends Payload

object Payload {

  implicit def payload2HttpResponse(payload: Payload): Box[Throwable, HttpResponse] = payload match {
    case PayloadSuccess(data) => Ok(JsObject(
      "status" -> 200,
      "data" -> data,
      "error" -> JsNull
    ))
    case PayloadError(e) => Ok(JsObject(
      "status" -> 404,
      "data" -> JsNull,
      "error" -> e
    ))
  }

}

