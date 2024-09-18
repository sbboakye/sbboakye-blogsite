package com.sbboakye.blog.db

import pureconfig.*
import pureconfig.generic.derivation.default.*

case class Port(number: Int) derives ConfigReader

case class DBConfig(
    driver: String,
    host: String,
    port: Port,
    database: String,
    user: String,
    password: String
) derives ConfigReader
