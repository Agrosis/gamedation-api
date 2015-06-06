package com.gamedation.api

import com.gamedation.api.controllers.game.{GetGame, Upvote, Submit}
import com.gamedation.api.controllers.{Games, ProcessLink, Index}
import com.gamedation.api.environment.Environment
import com.gamedation.api.models.Game
import com.plasmaconduit.framework.PlasmaConduit
import com.plasmaconduit.framework.routes.containers.{HttpPostMethodRoutes, HttpPutMethodRoutes, HttpRoutes, HttpGetMethodRoutes}
import com.plasmaconduit.framework.routes.destinations.HttpPathRoute
import com.plasmaconduit.validation.{Success, Failure}

import scala.util.matching.Regex
import com.plasmaconduit.framework.routes.PathRegexes._

object Gamedation {

  val routes = HttpRoutes(
    HttpGetMethodRoutes(
      HttpPathRoute("/", Index()),
      HttpPathRoute("/link", ProcessLink()),
      HttpPathRoute(new Regex(s"/game/$number", "gameId"), GetGame()),
      HttpPathRoute("/games", Games())
    ),
    HttpPostMethodRoutes(
      HttpPathRoute(new Regex(s"/game/upvote/$number", "gameId"), Upvote())
    ),
    HttpPutMethodRoutes(
      HttpPathRoute("/game/submit", Submit())
    )
  )

  def main(args: Array[String]) = {
    Environment.getEnvironment().initialize() match {
      case Success(_) => {
        val server = PlasmaConduit(
          port         = 1350,
          middleware   = List(),
          defaultRoute = Index(),
          routes       = routes
        )
        server.run()
      }
      case Failure(f) => {
        println(f.getMessage)
      }
    }
  }

}
