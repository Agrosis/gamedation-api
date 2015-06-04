package com.gamedation.api.models

sealed trait Status

final case object Normal extends Status
final case object Curator extends Status
final case object Admin extends Status

