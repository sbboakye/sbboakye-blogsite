package com.sbboakye.blog.db

import doobie.*
import doobie.implicits.*
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import cats.*
import cats.data.*
import cats.effect.*
import cats.implicits.*
import doobie.hikari.HikariTransactor
import pureconfig.*
import pureconfig.ConfigReader.Result
import pureconfig.error.ConfigReaderFailures

object Db {

  private def configFailuresToThrowable(failures: ConfigReaderFailures): Throwable =
    new RuntimeException(s"Failed to load configuration: ${failures.toList.mkString(", ")}")

  private def load[F[_]](using F: Async[F]): F[DBConfig] =
    val getConfig: Result[DBConfig] = ConfigSource.default.at("postgres").load[DBConfig]

    getConfig match
      case Right(config)  => F.pure(config)
      case Left(failures) => F.raiseError(configFailuresToThrowable(failures))

  def createTransactor[F[_]](using
      F: Async[F]
  ): F[Resource[F, HikariTransactor[F]]] =
    load[F]
      .map { config =>
        for {
          ec <- ExecutionContexts.fixedThreadPool[F](32)
          xa <- HikariTransactor.newHikariTransactor[F](
            driverClassName = config.driver,
            url = s"jdbc:postgresql://${config.host}:${config.port.number}/${config.database}",
            user = config.user,
            pass = config.password,
            connectEC = ec,
            logHandler = None
          )
        } yield xa
      }
      .handleErrorWith(e => F.raiseError(e))
}
