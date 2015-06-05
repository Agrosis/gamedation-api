package com.gamedation.api.services.production

import com.gamedation.api.services.interfaces.UserServiceComponent

trait ProductionUserService extends UserServiceComponent {

  val users = new UserServiceImpl

  final class UserServiceImpl extends UserService {

  }

}
