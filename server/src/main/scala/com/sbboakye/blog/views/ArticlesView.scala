package com.sbboakye.blog.views

import HomeView.Home
import cats.*
import cats.effect.*
import cats.syntax.all.*
import com.sbboakye.blog.repositories.Articles
import com.sbboakye.blog.services.ArticleService
import com.sbboakye.blog.views.htmx.HtmxAttributes
import scalatags.Text
import scalatags.Text.all.*

class ArticlesView[F[_]] private (articles: Articles[F])(using F: Concurrent[F]) {

  private val articleService: ArticleService[F] = ArticleService[F](articles)

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

//  class DetailView(id: UUID) extends Home {
//    override val bodyContents: Text.TypedTag[String] =
//      val detailArticle = articleService.findById(id)
//      div(
//        p(detailArticle.title),
//        p(detailArticle.content),
//        p(detailArticle.author),
//        p(detailArticle.updated_date.toString),
//        button(
//          "Edit",
//          HtmxAttributes.get(s"/${detailArticle.id}/edit"),
//          HtmxAttributes.target("#div-body")
//        )
//      )
//  }
//
//  class FormView(maybeId: Option[UUID]) extends Home {
//    override val bodyContents: Text.TypedTag[String] =
//      val detailArticle = articleService.findById(maybeId)
//
//      form(
//        HtmxAttributes.put(s"/${detailArticle.id}/update"),
//        input(
//          `type`      := "text",
//          name        := "title",
//          placeholder := "Title",
//          id          := "title",
//          value       := detailArticle.title
//        ),
//        input(
//          `type`      := "text",
//          name        := "content",
//          placeholder := "Content",
//          id          := "content",
//          value       := detailArticle.content
//        ),
//        button(
//          `type` := "submit",
//          "Update"
//        )
//      )
//  }
//
//  object CreateView extends Home
//
//  class UpdateView(id: UUID, title: String, content: String) extends Home {
//    override val bodyContents: Text.TypedTag[String] =
//      articleService.update(id, title, content)
//      ListView.render
//  }
//
//  object DeleteView extends Home

}

object ArticlesView {
  def apply[F[_]: Concurrent](articles: Articles[F]) = new ArticlesView[F](articles)
}
