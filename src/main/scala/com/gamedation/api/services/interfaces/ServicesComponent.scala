package com.gamedation.api.services.interfaces

import com.gamedation.api.database.Database

trait ServicesComponent extends Database with GameServiceComponent with UserServiceComponent
