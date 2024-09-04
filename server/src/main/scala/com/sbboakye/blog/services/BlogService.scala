package com.sbboakye.blog.services

import cats.syntax.all.*
import cats.effect.Sync
import com.sbboakye.blog.views.BlogsView.*
import org.http4s.dsl.Http4sDsl
import org.http4s.*
import org.http4s.scalatags.*
import org.http4s.server.Router

class BlogService[F[_]: Sync] {

  private val dsl: Http4sDsl[F] = new Http4sDsl[F] {}
  import dsl.*

  private val prefix = "/"

  private val listViewRoute: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    Ok(ListView.render)
  }

  private val detailViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / UUIDVar(blogId) =>
      Ok(DetailView.render)
  }

  private val createViewRoute: HttpRoutes[F] = HttpRoutes.of[F] { case POST -> Root / "create" =>
    Ok(CreateView.render)
  }

  private val updateViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case PUT -> Root / UUIDVar(blogId) / "edit" =>
      Ok(UpdateView.render)
  }

  private val deleteViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case DELETE -> Root / UUIDVar(blogId) / "delete" =>
      Ok(DeleteView.render)
  }

  val routes: HttpRoutes[F] = Router(
    prefix -> (listViewRoute <+> detailViewRoute <+> createViewRoute <+> updateViewRoute <+> deleteViewRoute)
  )

}

object BlogService
