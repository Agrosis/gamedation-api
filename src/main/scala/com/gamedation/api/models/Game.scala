package com.gamedation.api.models

final case class Game(id: Long, site: GameSite, link: String, name: String, description: String, platforms: GamePlatforms, images: List[String], points: Int, poster: Member)
