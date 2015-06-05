package com.gamedation.api.environment

import com.gamedation.api.services.interfaces.ServicesComponent

trait Environment extends ServicesComponent

object Environment {

  def getEnvironment(): Environment = {
    ProductionEnvironment
  }

}
