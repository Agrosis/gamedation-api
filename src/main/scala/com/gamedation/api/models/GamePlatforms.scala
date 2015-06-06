package com.gamedation.api.models

import com.plasmaconduit.json.{JsObject, JsValue}

final case class GamePlatforms(windows: Boolean, mac: Boolean, linux: Boolean, browser: Boolean, iOS: Boolean, android: Boolean) {

  def toJson(): JsValue = JsObject(
    "windows" -> windows,
    "mac" -> mac,
    "linux" -> linux,
    "browser" -> browser,
    "iOS" -> iOS,
    "android" -> android
  )

}
