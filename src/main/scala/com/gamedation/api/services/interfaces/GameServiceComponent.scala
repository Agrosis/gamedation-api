package com.gamedation.api.services.interfaces

import com.gamedation.api.models.Game

trait GameServiceComponent {

  val games: GameService

  trait GameService {

    def getGameById(id: Long): Option[Game]

    def createGame(): Option[Game]

    def voteGame(game: Long, member: Long): Option[Int]

  }

}
