package com.gamedation.api.services.interfaces

import com.gamedation.api.models.{GameSite, Game}

trait GameServiceComponent {

  val games: GameService

  trait GameService {

    def getGameById(id: Long): Option[Game]

    def createGame(link: String, name: String, description: String, windows: Boolean, mac: Boolean, linux: Boolean, browser: Boolean, iOS: Boolean, android: Boolean, images: List[String], poster: Long, site: GameSite): Option[Game]

    def voteGame(game: Long, member: Long): Option[Int]

    def hasUpvoted(game: Long, member: Long): Boolean

    def getImages(game: Long): List[String]

    def addImage(game: Long, link: String): Option[Long]

    def getGamesFor(time: Long): List[Game]

  }

}
