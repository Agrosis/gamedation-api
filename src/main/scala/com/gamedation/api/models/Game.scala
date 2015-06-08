package com.gamedation.api.models

import com.plasmaconduit.json.{JsObject, JsValue}

final case class Game(id: Long, site: GameSite, link: String, name: String, description: String, platforms: GamePlatforms, images: List[String], points: Int, poster: Member) {

  def toJson(upvoted: Boolean = false): JsValue = JsObject(
    "id" -> id,
    "site" -> site.toString,
    "link" -> link,
    "name" -> name,
    "description" -> description,
    "platforms" -> platforms.toJson,
    "images" -> images,
    "points" -> points,
    "poster" -> poster.id,
    "posterAvatar" -> poster.avatar(64),
    "upvoted" -> upvoted
  )

}
