package com.gamedation.api.models

sealed trait User

final case object Guest extends User
final case class Member(id: Long, status: Status, username: String, email: String, password: String)

