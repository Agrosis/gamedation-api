package com.gamedation.api.validators

import com.plasmaconduit.json.{JsObject, JsValue, JsReader}
import com.plasmaconduit.validation.{Success, Failure, Validation}

final case class CommentForm(text: String)

object CommentForm {

  implicit object CommentFormJsReader extends JsReader[CommentForm] {

    override type JsReaderFailure = String

    override def read(value: JsValue): Validation[CommentFormJsReader.JsReaderFailure, CommentForm] = value match {
      case JsObject(o) => {
        val f = for (
          text <- o.get("text").flatMap(_.as[String].toOption)
        ) yield {
          CommentForm(text)
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

