package com.gamedation.api.actions

import com.gamedation.api.environment.Environment
import com.gamedation.api.models.{Guest, User}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}

object InjectedAction {

  final case class InjectedRequest(env: Environment, user: User, private val request: HttpRequest)

  def apply(action: InjectedRequest => Box[Throwable, HttpResponse])(implicit request: HttpRequest): Box[Throwable, HttpResponse] = {
    val env = Environment.getEnvironment()

    val user = for (
      token <- request.headers.get("Authorization");
      email <- env.members.validateToken(token).toOption;
      member <- env.members.getMemberByEmail(email)
    ) yield member

    action(InjectedRequest(env, user.getOrElse(Guest), request))
  }

}
