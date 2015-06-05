package com.gamedation.api.services.production

import com.gamedation.api.models.Game
import com.gamedation.api.services.interfaces.GameServiceComponent

import scala.slick.driver.MySQLDriver.simple._

trait ProductionGameService extends GameServiceComponent { this: ProductionServices =>

  val games = new GameServiceImpl

  final class GameServiceImpl extends GameService {

    override def getGameById(id: Long): Option[Game] = database { implicit session =>
      None
    }

    override def createGame(): Option[Game] = database { implicit session =>
      None
    }

    override def voteGame(game: Long, member: Long): Option[Int] = database { implicit session =>
      val q = for {
        u <- tables.upvotes if u.gameId === game && u.memberId === member
      } yield u

      val c = if(q.delete > 0) -1
      else 1

      val p = for {
        u <- tables.games if u.id === game
      } yield u.points

      p.firstOption.map(x => p.update(x + c))
    }

  }

}
