package com.gamedation.api.models

sealed trait GameLink {

  def toString(): String

}

final case class GameJoltLink(id: Int, name: String, category: String) extends GameLink {

  override def toString(): String = s"http://gamejolt.com/$category/$name/$id/"

}

final case class SteamLink(id: Int) extends GameLink {

  override def toString(): String = s"http://store.steampowered.com/app/$id/"

}

