package com.sbboakye.blog.views

import HomePage.Home
import com.sbboakye.blog.repositories.BlogsRepository
import com.sbboakye.blog.services.BlogService
import scalatags.Text
import scalatags.Text.all.*

import java.util.UUID

object BlogsView {

  val blogsRepository = new BlogsRepository

  object ListView extends Home {
    override val bodyContents: Text.TypedTag[String] =
      div(
        BlogService(blogsRepository).listBlogs.map(blog =>
          a(href := s"/${blog.id}")(
            p(blog.title)
          )
        )
      )
  }

  class DetailView(id: UUID) extends Home {
    override val bodyContents: Text.TypedTag[String] =
      val detailBLog = BlogService(blogsRepository).getBlog(id)
      div(
        p(detailBLog.title),
        p(detailBLog.content),
        p(detailBLog.author),
        p(detailBLog.updated_date.toString)
      )
  }
  object CreateView extends Home
  object UpdateView extends Home
  object DeleteView extends Home

}
