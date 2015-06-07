package com.gamedation.api.validators

import com.plasmaconduit.json.{JsObject, JsValue, JsReader}
import com.plasmaconduit.validation.{Success, Failure, Validation}

final case class LogInForm(email: String, password: String)

object LogInForm {

  implicit object LogInFormJsReader extends JsReader[LogInForm] {

    override type JsReaderFailure = String

    override def read(value: JsValue): Validation[LogInFormJsReader.JsReaderFailure, LogInForm] = value match {
      case JsObject(o) => {
        val f = for (
          email <- o.get("email").flatMap(_.as[String].toOption);
          password <- o.get("password").flatMap(_.as[String].toOption)
        ) yield {
          LogInForm(email, password)
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

