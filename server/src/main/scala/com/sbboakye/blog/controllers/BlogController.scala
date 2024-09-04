package com.sbboakye.blog.controllers

import cats.effect.Sync

import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import org.http4s.scalatags as httpsTags // had to rename due to conflict with scalatags package above from lihaoyi
import httpsTags._

class BlogController[F[_]: Sync] {

  private val dsl: Http4sDsl[F] = new Http4sDsl[F] {}
  import dsl._

  private val getAllBlogs: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    val allBlogs = BlogViews.Child.render
    Ok(allBlogs)
  }

  val routes: HttpRoutes[F] = Router("/" -> getAllBlogs)

}

object BlogController
