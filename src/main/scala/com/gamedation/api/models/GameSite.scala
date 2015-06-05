package com.gamedation.api.models

sealed trait GameSite

case object Other extends GameSite
case object GameJolt extends GameSite
case object Steam extends GameSite

