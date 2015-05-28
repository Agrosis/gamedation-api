package com.gamedation.api

import com.gamedation.api.parsers.GameJoltLinkParser
import com.plasmaconduit.validation.{Failure, Success}

object Gamedation {

  def main(args: Array[String]) = {
    GameJoltLinkParser("http://gamejolt.com/games/strategy-sim/town-of-salem/23303/") match {
      case Success(game) => {
        println(s"${game.id} ${game.name} ${game.category}")
      }
      case Failure(e) => println(e)
    }
  }

}
