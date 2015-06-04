package com.gamedation.api

import com.gamedation.api.controllers.{ProcessLink, Index}
import com.plasmaconduit.framework.PlasmaConduit
import com.plasmaconduit.framework.routes.containers.{HttpRoutes, HttpGetMethodRoutes}
import com.plasmaconduit.framework.routes.destinations.HttpPathRoute

import scala.util.matching.Regex

object Gamedation {

  val routes = HttpRoutes(
    HttpGetMethodRoutes(
      HttpPathRoute("/", Index()),
      HttpPathRoute(new Regex("/link"), ProcessLink())
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
