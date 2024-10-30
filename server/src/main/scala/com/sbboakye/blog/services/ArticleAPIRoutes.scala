package com.sbboakye.blog.services

import cats.*
import cats.data.*
import cats.effect.*
import cats.syntax.all.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import io.circe.generic.auto.*
import org.typelevel.log4cats.Logger
import com.sbboakye.blog.domain.data.{Article, ArticleCreate}

import java.util.UUID

class ArticleAPIRoutes[F[_]: Concurrent: Logger] private (articleService: ArticleService[F])(using
    F: Monad[F]
) {

  private val dsl: Http4sDsl[F] = new Http4sDsl[F] {}
  import dsl.*

  given EntityEncoder[F, Seq[Article]]  = jsonEncoderOf[F, Seq[Article]]
  given EntityEncoder[F, Article]       = jsonEncoderOf[F, Article]
  given EntityDecoder[F, ArticleCreate] = jsonOf[F, ArticleCreate]
  given EntityEncoder[F, UUID]          = jsonEncoderOf[F, UUID]

  import articleService.*

  private val prefix = "/"

  private val listAPIRoute: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    findAll.flatMap(Ok(_))
  }

  private val detailAPIRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / UUIDVar(articleId) =>
      findById(articleId).flatMap(_.fold(NotFound())(Ok(_)))
  }

  private val createAPIRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root / "create" =>
      req.as[ArticleCreate].flatMap { articleCreate =>
        val article = Article(
          title = articleCreate.title,
          content = articleCreate.content,
          author = articleCreate.author
        )
        create(article).flatMap(Ok(_))
      }
  }

  private val updateAPIRoute: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ PUT -> Root / UUIDVar(articleId) =>
      ???
  }

  val routes: HttpRoutes[F] = Router(
    prefix -> (
      listAPIRoute
        <+>
          detailAPIRoute
          <+>
          updateAPIRoute
          <+>
          createAPIRoute
    )
  )

}

object ArticleAPIRoutes {
  def apply[F[_]: Concurrent: Logger](articleService: ArticleService[F]): ArticleAPIRoutes[F] =
    new ArticleAPIRoutes[F](articleService)
}
