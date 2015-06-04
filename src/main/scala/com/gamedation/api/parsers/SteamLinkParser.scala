package com.gamedation.api.parsers

import com.gamedation.api.models.SteamLink
import com.plasmaconduit.validation.Validation

import scala.util.parsing.combinator.RegexParsers

object SteamLinkParser extends RegexParsers {

  def protocol: Parser[String] = "http://" ^^ {
    case p => p
  }

  def domain: Parser[String] = "store.steampowered.com/app/"

  def id: Parser[Int] = "[0-9]+".r ^^ {
    case (gameId) => gameId.toInt
  }

  def fullParser: Parser[SteamLink] = protocol ~ domain ~ id ~ opt("/") ^^ {
    case(_ ~ _ ~ gid ~ _) => SteamLink(gid)
  }

  def apply(url: String): Validation[String, SteamLink] = {
    parse(fullParser, url) match {
      case Success(link, _) => com.plasmaconduit.validation.Success(link)
      case NoSuccess(error, _) => com.plasmaconduit.validation.Failure(error)
    }
  }

}
