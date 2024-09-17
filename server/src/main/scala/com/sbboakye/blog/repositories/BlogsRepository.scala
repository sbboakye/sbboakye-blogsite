package com.sbboakye.blog.repositories

import com.sbboakye.blog.domain.data.Article
import java.util.UUID

class BlogsRepository {

  def listBlogs: Seq[Article] = Article.seed

  def getBlog(id: UUID): Article = Article.seed.find(_.id == id).get

  def createBlog: Article = ???

  def updateBlog(id: UUID, title: String, content: String): Option[Article] =
    val blog = getBlog(id)
    println(s"This is the blog: $blog")
    val afterUpdate = Some(blog.copy(title = title, content = content))
    println(s"After update: $afterUpdate")
    afterUpdate

  def deleteBlog(UUID: UUID): UUID = ???

}

object BlogsRepository
