package com.sbboakye.blog.config

import cats.effect.*
import doobie.LogHandler
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import doobie.util.log.LogEvent

object Database {

  private def printSqlLogHandler[F[_]](using F: Async[F]): LogHandler[F] = new LogHandler[F] {
    def run(logEvent: LogEvent): F[Unit] =
      F.pure {
        println(logEvent.sql)
      }
  }

  def makeDbResource[F[_]: Async](config: DBConfig): Resource[F, HikariTransactor[F]] =
    for {
      ec <- ExecutionContexts.fixedThreadPool[F](config.nThreads)
      xa <- HikariTransactor.newHikariTransactor[F](
        driverClassName = config.driver,
        url = s"jdbc:postgresql://${config.host}:${config.port}/${config.database}",
        user = config.user,
        pass = config.password,
        connectEC = ec,
        logHandler = Some(printSqlLogHandler)
      )
    } yield xa
}
