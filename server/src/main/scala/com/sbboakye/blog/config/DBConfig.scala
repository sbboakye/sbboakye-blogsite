package com.sbboakye.blog.config

import pureconfig.*
import pureconfig.generic.derivation.default.*

case class DBConfig(
    nThreads: Int,
    driver: String,
    host: String,
    port: Int,
    database: String,
    user: String,
    password: String
) derives ConfigReader
