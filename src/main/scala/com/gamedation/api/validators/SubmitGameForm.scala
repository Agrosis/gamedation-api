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

    private def validateLink(link: String): Option[String] = {
      if(validLink(link)) Some(link)
      else None
    }

    private def validateName(name: String): Option[String] = {
      if(name.length <= 48) Some(name)
      else None
    }

    private def validateDescription(description: String): Option[String] = {
      if(description.length <= 100) Some(description)
      else None
    }

    private def validateImages(images: List[String]): Option[List[String]] = {
      if(images.length > 0 && images.length <= 6 && images.filter(l => !validLink(l)).length == 0) Some(images)
      else None
    }

    private def validLink(link: String): Boolean = link.startsWith("http") || link.startsWith("https")

    override def read(value: JsValue): Validation[SubmitGameFormJsReader.JsReaderFailure, SubmitGameForm] = value match {
      case JsObject(o) => {
        val f = for (
          link <- o.get("link").flatMap(_.as[String].toOption).flatMap(validateLink);
          name <- o.get("name").flatMap(_.as[String].toOption).flatMap(validateName);
          description <- o.get("description").flatMap(_.as[String].toOption).flatMap(validateDescription);
          windows <- o.get("windows").flatMap(_.as[Boolean].toOption);
          mac <- o.get("mac").flatMap(_.as[Boolean].toOption);
          linux <- o.get("linux").flatMap(_.as[Boolean].toOption);
          browser <- o.get("browser").flatMap(_.as[Boolean].toOption);
          iOS <- o.get("iOS").flatMap(_.as[Boolean].toOption);
          android <- o.get("android").flatMap(_.as[Boolean].toOption);
          images <- o.get("images").flatMap(_.as[List[String]].toOption).flatMap(validateImages)
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

