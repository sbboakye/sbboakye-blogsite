package com.sbboakye.blog

import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.comcast.ip4s.{ipv4, port}
import com.sbboakye.blog.controllers.BlogsController
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Server

object Application extends IOApp.Simple {

  override def run: IO[Unit] = makeServer.use(_ => IO.println("Server has started...") *> IO.never)

  val makeServer = for {
    server <- EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(BlogsController[IO].routes.orNotFound)
      .build
  } yield server

}
