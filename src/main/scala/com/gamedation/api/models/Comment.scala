package com.gamedation.api.models

import com.plasmaconduit.json.{JsValue, JsObject}

final case class Comment(id: Long, gameId: Long, member: Member, text: String, time: Long, children: List[Comment]) {

  def toJson(): JsValue = JsObject(
    "id" -> id,
    "gameId" -> gameId,
    "poster" -> member.toJson(),
    "text" -> text,
    "time" -> time,
    "children" -> children.map(_.toJson())
  )

}
