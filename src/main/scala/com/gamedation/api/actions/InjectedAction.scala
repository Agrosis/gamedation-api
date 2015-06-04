package com.gamedation.api.actions

import com.gamedation.api.environment.Environment
import com.gamedation.api.models.{Guest, User}
import com.plasmaconduit.conveyance.Box
import com.plasmaconduit.framework.{HttpResponse, HttpRequest}

object InjectedAction {

  final case class InjectedRequest(env: Environment, user: User, private val request: HttpRequest)

  def apply(action: InjectedRequest => Box[Throwable, HttpResponse])(implicit request: HttpRequest): Box[Throwable, HttpResponse] = {
    action(InjectedRequest(Environment.getEnvironment(), Guest, request))
  }

}
