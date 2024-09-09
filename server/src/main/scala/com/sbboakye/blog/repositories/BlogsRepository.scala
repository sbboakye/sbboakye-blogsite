package com.sbboakye.blog.repositories

import com.sbboakye.blog.domain.Blog

import java.util.UUID

class BlogsRepository {

  def listBlogs: Seq[Blog]         = Blog.dummies
  def getBlog(id: UUID): Blog      = Blog.dummies.find(_.id == id).get
  def createBlog: Blog             = ???
  def updateBlog(UUID: UUID): UUID = ???
  def deleteBlog(UUID: UUID): UUID = ???

}

object BlogsRepository
