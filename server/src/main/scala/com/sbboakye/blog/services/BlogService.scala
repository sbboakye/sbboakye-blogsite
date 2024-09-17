package com.sbboakye.blog.services

import com.sbboakye.blog.domain.Article
import com.sbboakye.blog.repositories.BlogsRepository

import java.util.UUID

class BlogService(blogsRepository: BlogsRepository) {

  def listBlogs: Seq[Article] = blogsRepository.listBlogs

  def getBlog(id: UUID): Article = blogsRepository.getBlog(id)

  def updateBlog(id: UUID, title: String, content: String): Option[Article] =
    blogsRepository.updateBlog(id, title, content)
}

object BlogService {
  def apply(repository: BlogsRepository): BlogService = {
    new BlogService(repository)
  }
}
