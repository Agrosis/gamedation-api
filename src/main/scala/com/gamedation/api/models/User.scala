package com.gamedation.api.models

import com.plasmaconduit.json.{JsValue, JsObject}

sealed trait User

final case object Guest extends User
final case class Member(id: Long, status: Status, username: String, email: String) extends User {

  def toJson(): JsValue = JsObject(
    "id" -> id,
    "status" -> status.toInt,
    "username" -> username,
    "email" -> email
  )

}

