package com.sbboakye.blog.services

import cats.effect.Sync
import com.sbboakye.blog.views.BlogsView
import org.http4s.dsl.Http4sDsl
import org.http4s.{scalatags as httpsTags, *}
import org.http4s.scalatags.*
import org.http4s.server.Router

class BlogService[F[_]: Sync] {

  private val dsl: Http4sDsl[F] = new Http4sDsl[F] {}
  import dsl.*

  private val listBlogs: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    val allBlogs = BlogsView.Child.render
    Ok(allBlogs)
  }

  val routes: HttpRoutes[F] = Router("/" -> listBlogs)

}

object BlogService
