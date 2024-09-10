package com.sbboakye.blog.views

import HomePage.Home
import com.sbboakye.blog.repositories.BlogsRepository
import com.sbboakye.blog.services.BlogService
import com.sbboakye.blog.views.htmx.HtmxAttributes
import scalatags.Text
import scalatags.Text.all.*

import java.util.UUID

object BlogsView {

  val blogsRepository = new BlogsRepository

  object ListView extends Home {
    override val bodyContents: Text.TypedTag[String] =
      div(
        BlogService(blogsRepository).listBlogs.map(blog =>
          a(
            href := "#",
            HtmxAttributes.get(s"/${blog.id}"),
            HtmxAttributes.target("#div-body")
          )(
            p(blog.title)
          )
        )
      )
  }

  class DetailView(id: UUID) extends Home {
    override val bodyContents: Text.TypedTag[String] =
      val detailBlog = BlogService(blogsRepository).getBlog(id)
      div(
        p(detailBlog.title),
        p(detailBlog.content),
        p(detailBlog.author),
        p(detailBlog.updated_date.toString),
        button(
          "Edit",
          HtmxAttributes.get(s"/${detailBlog.id}/edit"),
          HtmxAttributes.target("#div-body")
        )
      )
  }

  class FormView(maybeId: Option[UUID]) extends Home {
    override val bodyContents: Text.TypedTag[String] =
      val detailBlog = BlogService(blogsRepository).getBlog(maybeId.get)

      form(
        HtmxAttributes.put(s"/${detailBlog.id}/update"),
        HtmxAttributes.headers("{'Content-Type': 'application/json'}"),
        input(
          `type`      := "text",
          name        := "title",
          placeholder := "Title",
          id          := "title",
          value       := detailBlog.title
        ),
        input(
          `type`      := "text",
          name        := "content",
          placeholder := "Content",
          id          := "content",
          value       := detailBlog.content
        ),
        button(
          `type` := "submit",
          "Update"
        )
      )
  }

  object CreateView extends Home

  class UpdateView(id: UUID, title: String, content: String) extends Home {
    override val bodyContents: Text.TypedTag[String] =
      BlogService(blogsRepository).updateBlog(id, title, content)
      ListView.render
  }

  object DeleteView extends Home

}
