package com.gamedation.api.validators

import com.plasmaconduit.json.{JsObject, JsValue, JsReader}
import com.plasmaconduit.validation.{Success, Failure, Validation}

final case class SubmitGameForm(link: String,
                                name: String,
                                description: String,
                                windows: Boolean,
                                mac: Boolean,
                                linux: Boolean,
                                browser: Boolean,
                                iOS: Boolean,
                                android: Boolean,
                                images: List[String])

object SubmitGameForm {

  implicit object SubmitGameFormJsReader extends JsReader[SubmitGameForm] {

    override type JsReaderFailure = String

    override def read(value: JsValue): Validation[SubmitGameFormJsReader.JsReaderFailure, SubmitGameForm] = value match {
      case JsObject(o) => {
        val f = for (
          link <- o.get("link").flatMap(_.as[String].toOption);
          name <- o.get("name").flatMap(_.as[String].toOption);
          description <- o.get("description").flatMap(_.as[String].toOption);
          windows <- o.get("windows").flatMap(_.as[Boolean].toOption);
          mac <- o.get("mac").flatMap(_.as[Boolean].toOption);
          linux <- o.get("linux").flatMap(_.as[Boolean].toOption);
          browser <- o.get("browser").flatMap(_.as[Boolean].toOption);
          iOS <- o.get("iOS").flatMap(_.as[Boolean].toOption);
          android <- o.get("android").flatMap(_.as[Boolean].toOption);
          images <- o.get("images").flatMap(_.as[List[String]].toOption)
        ) yield {
          SubmitGameForm(link, name, description, windows, mac, linux, browser, iOS, android, images)
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

