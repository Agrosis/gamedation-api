package com.gamedation.api.models

import com.plasmaconduit.json.{JsValue, JsObject}
import com.plasmaconduit.waterhouse.{Md5, Hash}

sealed trait User

final case object Guest extends User

final case class Member(id: Long, status: Status, username: String, email: String) extends User {

  def avatar(size: Int): String = {
    "https://gravatar.com/avatar/" + Hash.digest(Md5, email.replaceAll(" ", "").toLowerCase).toString().toLowerCase + s"?s=$size&d=http://gmdcdn.s3-website-us-west-1.amazonaws.com/images/avi.png"
  }

  def toJson(): JsValue = JsObject(
    "id" -> id,
    "status" -> status.toInt,
    "username" -> username,
//    "email" -> email,
    "avatar" -> avatar(64)
  )

}

