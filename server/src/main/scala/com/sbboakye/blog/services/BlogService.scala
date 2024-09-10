package com.sbboakye.blog.services

import com.sbboakye.blog.domain.Blog
import com.sbboakye.blog.repositories.BlogsRepository

import java.util.UUID

class BlogService(blogsRepository: BlogsRepository) {

  def listBlogs: Seq[Blog] = blogsRepository.listBlogs

  def getBlog(id: UUID): Blog = blogsRepository.getBlog(id)

  def updateBlog(id: UUID, title: String, content: String): Option[Blog] =
    blogsRepository.updateBlog(id, title, content)
}

object BlogService {
  def apply(repository: BlogsRepository): BlogService = {
    new BlogService(repository)
  }
}
