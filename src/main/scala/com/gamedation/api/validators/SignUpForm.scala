package com.gamedation.api.validators

import com.plasmaconduit.json.{JsObject, JsValue, JsReader}
import com.plasmaconduit.validation.{Success, Failure, Validation}

final case class SignUpForm(username: String, password: String, email: String)

object SignUpForm {

  implicit object SignUpFormJsReader extends JsReader[SignUpForm] {

    override type JsReaderFailure = String

    private def validateUsername(username: String): Option[String] = {
      if(username.length > 0 && username.length <= 20 && username.matches("[A-Za-z0-9-_.]+")) Some(username)
      else None
    }

    private def validatePassword(password: String): Option[String] = {
      if(password.length >= 8 && password.length <= 128) Some(password)
      else None
    }

    private def validateEmail(email: String): Option[String] = {
      if(email.length >= 5 && email.length <= 128 && email.matches(".+\\@.+\\..+")) Some(email)
      else None
    }

    override def read(value: JsValue): Validation[SignUpFormJsReader.JsReaderFailure, SignUpForm] = value match {
      case JsObject(o) => {
        val f = for (
          username <- o.get("username").flatMap(_.as[String].toOption).flatMap(validateUsername);
          password <- o.get("password").flatMap(_.as[String].toOption).flatMap(validatePassword);
          email <- o.get("email").flatMap(_.as[String].toOption).flatMap(validateEmail)
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

