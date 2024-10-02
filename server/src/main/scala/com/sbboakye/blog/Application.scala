package com.sbboakye.blog

import cats.*
import cats.effect.*
import cats.implicits.*
import com.sbboakye.blog.config.{AppConfig, Database, Db}
import com.sbboakye.blog.config.syntax.*
import com.sbboakye.blog.services.{ArticleRoutes, ArticleService}
import com.sbboakye.blog.views.ArticleViews
import org.http4s.HttpRoutes
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.{ErrorAction, ErrorHandling, Logger as http4sLogger}
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import pureconfig.*

object Application extends IOApp.Simple {
  given logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  private def allService(service: HttpRoutes[IO]): HttpRoutes[IO] =
    val loggerService: HttpRoutes[IO] = http4sLogger.httpRoutes[IO](
      logHeaders = true,
      logBody = true,
      redactHeadersWhen = _ => false,
      logAction = Some((msg: String) => Logger[IO].info(msg))
    )(service)

    val errorActionService: HttpRoutes[IO] = ErrorAction.httpRoutes[IO](
      loggerService,
      (req, thr) => Logger[IO].error(s"Error from request: $req, with error: ${thr.getMessage}")
    )

    val errorHandlingService: HttpRoutes[IO] =
      ErrorHandling.httpRoutes[IO](errorActionService)

    errorHandlingService

  override def run: IO[Unit] = makeServer

  private val makeServer: IO[Unit] =
    ConfigSource.default.loadF[IO, AppConfig].flatMap { case AppConfig(dbConfig, emberConfig) =>
      val appResource = for {
        xa             <- Database.makeDbResource[IO](dbConfig)
        articlesRepo   <- Db[IO](xa)
        articleService <- ArticleService[IO](articlesRepo)
        articleViews   <- ArticleViews[IO](articleService)
        server <- EmberServerBuilder
          .default[IO]
          .withHost(emberConfig.host)
          .withPort(emberConfig.port)
          .withHttpApp(allService(ArticleRoutes(articleViews).routes).orNotFound)
          .build
      } yield server

      appResource.use(_ => IO.println("Server has started...") *> IO.never)
    }

}
