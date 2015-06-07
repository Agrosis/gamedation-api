package com.gamedation.api.validators

import com.plasmaconduit.json.{JsObject, JsValue, JsReader}
import com.plasmaconduit.validation.{Success, Failure, Validation}

final case class WebToken(email: String)

object WebToken {

  implicit object WebTokenJsReader extends JsReader[WebToken] {

    override type JsReaderFailure = String

    override def read(value: JsValue): Validation[WebTokenJsReader.JsReaderFailure, WebToken] = value match {
      case JsObject(o) => {
        val f = for (
          email <- o.get("email").flatMap(_.as[String].toOption)
        ) yield {
          WebToken(email)
        }

        f match {
          case Some(res) => Success(res)
          case None => Failure("Invalid json object format.")
        }
      }
      case _ => Failure("Invalid json object.")
    }

  }

}

