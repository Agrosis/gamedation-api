package com.gamedation.api.models

sealed trait Status {

  def toInt(): Int = this match {
    case Normal => 1
    case Curator => 2
    case Admin => 3
  }

  def isCurator(): Boolean = toInt >= 2

}

object Status {

  def fromInt(i: Int) = i match {
    case 1 => Normal
    case 2 => Curator
    case 3 => Admin
    case _ => Normal
  }

}

final case object Normal extends Status
final case object Curator extends Status
final case object Admin extends Status

