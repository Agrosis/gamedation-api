package com.gamedation.api.services.production

import com.gamedation.api.services.interfaces.ServicesComponent

trait ProductionServices extends ServicesComponent with ProductionGameService with ProductionUserService
