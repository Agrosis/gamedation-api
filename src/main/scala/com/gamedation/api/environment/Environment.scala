package com.gamedation.api.environment

trait Environment

object Environment {

  def getEnvironment(): Environment = {
    ProductionEnvironment
  }

}
