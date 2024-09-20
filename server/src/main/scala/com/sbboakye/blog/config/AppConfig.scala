package com.sbboakye.blog.config

import pureconfig.*
import pureconfig.generic.derivation.default.*

case class AppConfig(
    dbConfig: DBConfig,
    emberConfig: EmberConfig
) derives ConfigReader
