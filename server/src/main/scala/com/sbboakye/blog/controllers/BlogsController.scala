package com.sbboakye.blog.controllers

import cats.effect.Concurrent
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

class BlogsController[F[_]: Concurrent] {

  val dsl: Http4sDsl[F] = new Http4sDsl[F] {}
  import dsl._

  private val getAllBlogs: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    Ok("Hello World")
  }

  val routes: HttpRoutes[F] = Router("/" -> getAllBlogs)

}

object BlogsController
