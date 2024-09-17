package com.sbboakye.blog.services

import cats.syntax.all.*
import cats.effect.{Concurrent, Sync}
import com.sbboakye.blog.domain.Article
import com.sbboakye.blog.views.BlogsView.*
import org.http4s.dsl.Http4sDsl
import org.http4s.*
import org.http4s.circe.*
import org.http4s.scalatags.*
import org.http4s.server.Router
import io.circe.generic.auto.*

import java.util.UUID

class BlogRoutes[F[_]: Concurrent] {

  private val dsl: Http4sDsl[F] = new Http4sDsl[F] {}
  import dsl.*

  given EntityDecoder[F, Article] = jsonOf[F, Article]

  private val prefix = "/"

  private val listViewRoute: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    Ok(ListView.render)
  }

  private val detailViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / UUIDVar(blogId) =>
      Ok(DetailView(blogId).render)
  }

  private val createViewRoute: HttpRoutes[F] = HttpRoutes.of[F] { case POST -> Root / "create" =>
    Ok(CreateView.render)
  }

  private val formViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / UUIDVar(blogId) / "edit" =>
      Ok(FormView(Some(blogId)).render)
  }

  private val updateViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ PUT -> Root / UUIDVar(blogId) / "update" =>
      req.decode[UrlForm] { form =>
        val title   = form.getFirst("title").get
        val content = form.getFirst("content").get
        Ok(UpdateView(blogId, title, content).render)
      }
  }

  private val deleteViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case DELETE -> Root / UUIDVar(blogId) / "delete" =>
      Ok(DeleteView.render)
  }

  val routes: HttpRoutes[F] = Router(
    prefix -> (
      listViewRoute <+>
        detailViewRoute <+>
        createViewRoute <+>
        updateViewRoute <+>
        deleteViewRoute <+>
        formViewRoute
    )
  )

}

object BlogRoutes
