package com.sbboakye.blog

import cats.effect.{IO, IOApp, Resource}
import com.comcast.ip4s.{ipv4, port}
import com.sbboakye.blog.controllers.BlogsController
import org.http4s.HttpRoutes
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Server
import org.http4s.server.middleware.{ErrorAction, ErrorHandling, Logger as http4sLogger}
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Application extends IOApp.Simple {
  given logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  private val loggerService: HttpRoutes[IO] = http4sLogger.httpRoutes[IO](
    logHeaders = true,
    logBody = true,
    redactHeadersWhen = _ => false,
    logAction = Some((msg: String) => Logger[IO].info(msg))
  )(BlogsController[IO].routes)

  private val errorActionService: HttpRoutes[IO] = ErrorAction.httpRoutes[IO](
    loggerService,
    (req, thr) => Logger[IO].error(s"Error from request: $req, with error: ${thr.getMessage}")
  )

  private val errorHandlingService: HttpRoutes[IO] =
    ErrorHandling.httpRoutes[IO](errorActionService)

  override def run: IO[Unit] = makeServer.use(_ => IO.println("Server has started...") *> IO.never)

  private val makeServer: Resource[IO, Server] = for {
    server <- EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(errorHandlingService.orNotFound)
      .build
  } yield server

}
