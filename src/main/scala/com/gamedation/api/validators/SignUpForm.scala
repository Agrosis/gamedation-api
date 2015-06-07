package com.gamedation.api.validators

import com.plasmaconduit.json.{JsObject, JsValue, JsReader}
import com.plasmaconduit.validation.{Success, Failure, Validation}

final case class SignUpForm(username: String, password: String, email: String)

object SignUpForm {

  implicit object SignUpFormJsReader extends JsReader[SignUpForm] {

    override type JsReaderFailure = String

    override def read(value: JsValue): Validation[SignUpFormJsReader.JsReaderFailure, SignUpForm] = value match {
      case JsObject(o) => {
        val f = for (
          username <- o.get("username").flatMap(_.as[String].toOption);
          password <- o.get("password").flatMap(_.as[String].toOption);
          email <- o.get("email").flatMap(_.as[String].toOption)
        ) yield {
          SignUpForm(username, password, email)
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

