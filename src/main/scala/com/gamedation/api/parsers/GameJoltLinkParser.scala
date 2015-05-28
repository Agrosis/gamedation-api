package com.gamedation.api.parsers

import com.gamedation.api.models.GameJoltLink
import com.plasmaconduit.validation.Validation

import scala.util.parsing.combinator.RegexParsers

object GameJoltLinkParser extends RegexParsers {

  def protocol: Parser[String] = "http://" ^^ {
    case p => p
  }

  def domain: Parser[String] = "gamejolt.com/games/"

  def category: Parser[String] = "[A-z-]+".r ~ "/" ^^ {
    case (category ~ _) => category
  }

  def name: Parser[String] = "[^:\\r\\n/]+".r ~ opt("/") ^^ {
    case (name ~ _) => name
  }

  def id: Parser[Int] = "[0-9]+".r ~ opt("/") ^^ {
    case (gameId ~ _) => gameId.toInt
  }

  def fullParser: Parser[GameJoltLink] = protocol ~ domain ~ category ~ name ~ id ^^ {
    case(_ ~ _ ~ c ~ n ~ gid) => GameJoltLink(gid, n, c)
  }

  def apply(url: String): Validation[String, GameJoltLink] = {
    parse(fullParser, url) match {
      case Success(link, _) => com.plasmaconduit.validation.Success(link)
      case NoSuccess(error, _) => com.plasmaconduit.validation.Failure(error)
    }
  }

}
