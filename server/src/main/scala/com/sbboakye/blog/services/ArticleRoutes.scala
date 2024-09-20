package com.sbboakye.blog.services

import cats.*
import cats.effect.*
import cats.syntax.all.*
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.repositories.Articles
import com.sbboakye.blog.views.ArticlesView
import org.http4s.dsl.Http4sDsl
import org.http4s.*
import org.http4s.circe.*
import org.http4s.scalatags.*
import org.http4s.server.Router
import io.circe.generic.auto.*
import org.typelevel.log4cats.Logger

import java.util.UUID

class ArticleRoutes[F[_]: Concurrent: Logger] private (articles: Articles[F]) {

  private val dsl: Http4sDsl[F] = new Http4sDsl[F] {}
  import dsl.*

  given EntityDecoder[F, Article] = jsonOf[F, Article]

  val articlesView: ArticlesView[F] = ArticlesView[F](articles)

  private val prefix = "/"

  private val listViewRoute: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    articlesView.ListView().render.flatMap(Ok(_))
  }

//  private val detailViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
//    case GET -> Root / UUIDVar(blogId) =>
//      Ok(DetailView(blogId).render)
//  }
//
//  private val createViewRoute: HttpRoutes[F] = HttpRoutes.of[F] { case POST -> Root / "create" =>
//    Ok(CreateView.render)
//  }
//
//  private val formViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
//    case GET -> Root / UUIDVar(blogId) / "edit" =>
//      Ok(FormView(Some(blogId)).render)
//  }
//
//  private val updateViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
//    case req @ PUT -> Root / UUIDVar(blogId) / "update" =>
//      req.decode[UrlForm] { form =>
//        val title   = form.getFirst("title").get
//        val content = form.getFirst("content").get
//        Ok(UpdateView(blogId, title, content).render)
//      }
//  }
//
//  private val deleteViewRoute: HttpRoutes[F] = HttpRoutes.of[F] {
//    case DELETE -> Root / UUIDVar(blogId) / "delete" =>
//      Ok(DeleteView.render)
//  }

  val routes: HttpRoutes[F] = Router(
    prefix -> (
      listViewRoute
//        detailViewRoute <+>
//        createViewRoute <+>
//        updateViewRoute <+>
//        deleteViewRoute <+>
//        formViewRoute
    )
  )

}

object ArticleRoutes {
  def apply[F[_]: Concurrent: Logger](articles: Articles[F]): ArticleRoutes[F] =
    new ArticleRoutes[F](articles)
}
