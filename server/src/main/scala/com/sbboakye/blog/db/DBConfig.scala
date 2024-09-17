package com.sbboakye.blog.db

import cats.ApplicativeError
import pureconfig.*
import pureconfig.ConfigReader.Result
import pureconfig.error.ConfigReaderFailures
import pureconfig.generic.derivation.default.*

case class Port(number: Int) derives ConfigReader

case class DBConfig(
    host: String,
    port: Port,
    database: String,
    user: String,
    password: String
) derives ConfigReader

object DBConfig {

  def loadConfig[F[_]](using F: ApplicativeError[F, ConfigReaderFailures]): F[DBConfig] =
    val getConfig: Result[DBConfig] = ConfigSource.default.at("postgres").load[DBConfig]

    getConfig match
      case Right(config)  => F.pure(config)
      case Left(failures) => F.raiseError(failures)
}
