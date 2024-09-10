package com.sbboakye.blog.repositories

import com.sbboakye.blog.domain.Blog

import java.util.UUID

class BlogsRepository {

  def listBlogs: Seq[Blog] = Blog.dummies

  def getBlog(id: UUID): Blog = Blog.dummies.find(_.id == id).get

  def createBlog: Blog = ???

  def updateBlog(id: UUID, title: String, content: String): Option[Blog] =
    val blog = getBlog(id)
    println(s"This is the blog: $blog")
    val afterUpdate = Some(blog.copy(title = title, content = content))
    println(s"After update: $afterUpdate")
    afterUpdate

  def deleteBlog(UUID: UUID): UUID = ???

}

object BlogsRepository
