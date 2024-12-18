package com.sbboakye.blog.services

import cats.*
import cats.data.*
import cats.effect.*
import cats.syntax.all.*
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.views.ArticleViews
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import org.http4s.scalatags.*
import org.http4s.server.Router
import io.circe.generic.auto.*
import org.typelevel.log4cats.Logger

import java.util.UUID

class ArticleRoutes[F[_]: Concurrent: Logger] private (articleViews: ArticleViews[F])(using
    F: Monad[F]
) {

  private val dsl: Http4sDsl[F] = new Http4sDsl[F] {}
  import dsl.*

  given EntityDecoder[F, Article] = jsonOf[F, Article]

  import articleViews.*

  private val prefix = "/"

  private val listViewRoute: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    ListView().render.flatMap(Ok(_))
  }

  private val detailViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / UUIDVar(articleId) =>
      DetailView(articleId).render.flatMap(Ok(_))
  }

  private val createViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root / "create" =>
      for {
        form <- req.as[UrlForm]
        articleId <- createView(
          Article(
            title = form.getFirst("title").get,
            content = form.getFirst("content").get,
            author = form.getFirst("author").get
          )
        )
        response <- DetailView(articleId).render.flatMap(Ok(_))
      } yield response
  }

  private val formCreateViewRoute: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root / "create" =>
    FormView(None).render.flatMap(Ok(_))
  }

  private val formUpdateViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / UUIDVar(articleId) / "update" =>
      FormView(Some(articleId)).render.flatMap(Ok(_))
  }

  private val updateViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ PUT -> Root / UUIDVar(articleId) =>
      for {
        form <- req.as[UrlForm]
        maybeNewArticle <- updateView(
          articleId,
          Article(
            title = form.getFirst("title").get,
            content = form.getFirst("content").get,
            author = form.getFirst("author").get
          )
        )
        resp <- maybeNewArticle match {
          case None          => NotFound()
          case Some(article) => DetailView(articleId).render.flatMap(Ok(_))
        }
      } yield resp
  }

  val routes: HttpRoutes[F] = Router(
    prefix -> (
      listViewRoute
        <+>
          detailViewRoute
          <+>
          formUpdateViewRoute
          <+>
          updateViewRoute
          <+>
          formCreateViewRoute
          <+>
          createViewRoute
    )
  )

}

object ArticleRoutes {
  def apply[F[_]: Concurrent: Logger](articleViews: ArticleViews[F]): ArticleRoutes[F] =
    new ArticleRoutes[F](articleViews)
}
