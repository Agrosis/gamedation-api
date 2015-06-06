package com.gamedation.api.services.production

import com.gamedation.api.database.{DbUpvote, DbGameImage, DbGame}
import com.gamedation.api.models.{GamePlatforms, GameSite, Game}
import com.gamedation.api.services.interfaces.GameServiceComponent

import scala.slick.driver.MySQLDriver.simple._

trait ProductionGameService extends GameServiceComponent { this: ProductionServices =>

  val games = new GameServiceImpl

  final class GameServiceImpl extends GameService {

    override def getGameById(id: Long): Option[Game] = database { implicit session =>
      val q = for {
        g <- tables.games if g.id === id
      } yield g

      for (
        g <- q.firstOption;
        member <- members.getMemberById(g.poster)
      ) yield {
        Game(g.id, GameSite.fromInt(g.site), g.link, g.name, g.description, GamePlatforms(g.windows, g.mac, g.linux, g.browser, g.iOS, g.android), getImages(g.id), g.points, member)
      }
    }

    override def createGame(link: String, name: String, description: String, windows: Boolean, mac: Boolean, linux: Boolean, browser: Boolean, iOS: Boolean, android: Boolean, images: List[String], poster: Long, site: GameSite): Option[Game] = database { implicit session =>
      val id = (tables.games returning tables.games.map(_.id)) += DbGame(0, link, name, description, windows, mac, linux, browser, iOS, android, 0, poster, site.toInt, System.currentTimeMillis())
      voteGame(id, poster)

      images.foreach(link => addImage(id, link))

      getGameById(id)
    }

    override def voteGame(game: Long, member: Long): Option[Int] = database { implicit session =>
      val q = for {
        u <- tables.upvotes if u.gameId === game && u.memberId === member
      } yield u

      val change = if(q.delete == 0) {
        tables.upvotes.insert(DbUpvote(member, game, System.currentTimeMillis()))
        1
      } else -1

      val p = for {
        u <- tables.games if u.id === game
      } yield u.points

      p.firstOption.map(points => p.update(points + change))
    }

    override def hasUpvoted(game: Long, member: Long): Boolean = database { implicit session =>
      tables.upvotes.filter(u => u.gameId === game && u.memberId === member).exists.run
    }

    override def getImages(game: Long): List[String] = database { implicit session =>
      val q = for {
        i <- tables.gameImages if i.gameId === game
      } yield i.link
      q.list
    }

    override def addImage(game: Long, link: String): Option[Long] = database { implicit session =>
      if(tables.games.filter(_.id === game).exists.run)
        Some(((tables.gameImages) returning tables.gameImages.map(_.id)) += DbGameImage(0, game, link))
      else
        None
    }

    override def getGamesFor(time: Long): List[Game] = database { implicit session =>
      val q = for {
        g <- tables.games
      } yield g

      q.list.flatMap(g => {
        members.getMemberById(g.poster).map(p => {
          Game(g.id, GameSite.fromInt(g.site), g.link, g.name, g.description, GamePlatforms(g.windows, g.mac, g.linux, g.browser, g.iOS, g.android), getImages(g.id), g.points, p)
        })
      })
    }

  }

}
