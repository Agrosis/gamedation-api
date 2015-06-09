package com.gamedation.api.services.interfaces

import com.gamedation.api.models.{Member, GameSite, Game}

trait GameServiceComponent {

  val games: GameService

  trait GameService {

    def getGameById(id: Long): Option[Game]

    def createGame(link: String, name: String, description: String, windows: Boolean, mac: Boolean, linux: Boolean, browser: Boolean, iOS: Boolean, android: Boolean, images: List[String], poster: Long, site: GameSite): Option[Game]

    def voteGame(game: Long, member: Long): Option[Int]

    def hasUpvoted(game: Long, member: Long): Boolean

    def getUpvoters(game: Long): List[Long]

    def getImages(game: Long): List[String]

    def addImage(game: Long, link: String): Option[Long]

    def getLatestGames(): List[Game]

    def canUploadGame(member: Member): Boolean

    def getFeatured(time: Long): List[Game]

    def featureGame(gameId: Long): Boolean

    def isFeatured(gameId: Long): Boolean

  }

}
