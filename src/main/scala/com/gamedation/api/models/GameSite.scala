package com.gamedation.api.models

sealed trait GameSite {

  def toInt: Int = this match {
    case Other => 1
    case GameJolt => 2
    case Steam => 3
  }

}

object GameSite {

  def fromInt(i: Int) = i match {
    case 1 => Other
    case 2 => GameJolt
    case 3 => Steam
    case _ => Other
  }

}

case object Other extends GameSite
case object GameJolt extends GameSite
case object Steam extends GameSite

