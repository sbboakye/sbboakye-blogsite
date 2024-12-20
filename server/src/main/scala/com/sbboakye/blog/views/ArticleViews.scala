package com.sbboakye.blog.views

import HomeView.Home
import cats.*
import cats.effect.*
import cats.syntax.all.*
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.services.ArticleService
import com.sbboakye.blog.views.htmx.HtmxAttributes
import scalatags.Text
import scalatags.Text.all.*

import java.util.UUID

class ArticleViews[F[_]] private (articleService: ArticleService[F])(using F: Concurrent[F]) {

  class ListView extends Home {
    override val bodyContents: F[Text.TypedTag[String]] =
      articleService.findAll.flatMap { articles =>
        F.pure(
          div(
            articles.map { article =>
              a(
                href := "#",
                HtmxAttributes.get(s"/${article.id}"),
                HtmxAttributes.target("#div-body")
              )(
                p(article.title)
              )
            }
          )
        )
      }
  }

  class DetailView(id: UUID) extends Home {
    override val bodyContents: F[Text.TypedTag[String]] =
      println(s"Inside detail view")
      val detailArticle = articleService.findById(id)
      println(s"I got this giys: $detailArticle")
      detailArticle.map {
        case None => div(p("Not found"))
        case Some(article) =>
          div(
            p(article.title),
            p(article.content),
            p(article.author),
            p(article.updated_date.toString),
            button(
              "Edit",
              HtmxAttributes.get(s"/${article.id}/update"),
              HtmxAttributes.target("#div-body")
            )
          )
      }
  }

  class FormView(maybeId: Option[UUID]) extends Home {
    override val bodyContents: F[Text.TypedTag[String]] = maybeId match
      case None =>
        F.pure(
          form(
            HtmxAttributes.post("/create"),
            input(
              `type`      := "text",
              name        := "title",
              placeholder := "Title",
              id          := "title"
            ),
            input(
              `type`      := "text",
              name        := "content",
              placeholder := "Content",
              id          := "content"
            ),
            button(
              `type` := "submit",
              "Post"
            )
          )
        )
      case Some(uuid) =>
        val detailArticle = articleService.findById(uuid)
        detailArticle.map {
          case None => div(p("Not found"))
          case Some(article) =>
            form(
              HtmxAttributes.put(s"/${article.id}"),
              input(
                `type`      := "text",
                name        := "title",
                placeholder := "Title",
                id          := "title",
                value       := article.title
              ),
              input(
                `type`      := "text",
                name        := "content",
                placeholder := "Content",
                id          := "content",
                value       := article.content
              ),
              button(
                `type` := "submit",
                "Update"
              )
            )
        }
  }

  def createView(article: Article): F[UUID] = {
    articleService.create(article)
  }

  def updateView(id: UUID, article: Article): F[Option[Int]] = {
    articleService.update(id, article)
  }

}

object ArticleViews {
  def apply[F[_]](
      articleService: ArticleService[F]
  )(using F: Concurrent[F]): Resource[F, ArticleViews[F]] =
    Resource
      .eval(F.pure(new ArticleViews[F](articleService)))
}
