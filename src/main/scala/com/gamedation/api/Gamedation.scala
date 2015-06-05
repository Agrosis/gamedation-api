package com.gamedation.api

import com.gamedation.api.controllers.game.{Upvote, Submit}
import com.gamedation.api.controllers.{ProcessLink, Index}
import com.plasmaconduit.framework.PlasmaConduit
import com.plasmaconduit.framework.routes.containers.{HttpPostMethodRoutes, HttpPutMethodRoutes, HttpRoutes, HttpGetMethodRoutes}
import com.plasmaconduit.framework.routes.destinations.HttpPathRoute

import scala.util.matching.Regex
import com.plasmaconduit.framework.routes.PathRegexes._

object Gamedation {

  val routes = HttpRoutes(
    HttpGetMethodRoutes(
      HttpPathRoute("/", Index()),
      HttpPathRoute(new Regex("/link"), ProcessLink())
    ),
    HttpPostMethodRoutes(
      HttpPathRoute(new Regex(s"/game/upvote/$number", "gameid"), Upvote())
    ),
    HttpPutMethodRoutes(
      HttpPathRoute("/game/submit", Submit())
    )
  )

  def main(args: Array[String]) = {
    val server = PlasmaConduit(
      port         = 1350,
      middleware   = List(),
      defaultRoute = Index(),
      routes       = routes
    )
    server.run()
  }

}
