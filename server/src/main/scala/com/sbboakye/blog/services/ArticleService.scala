package com.sbboakye.blog.services

import cats.*
import cats.effect.*
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.repositories.Articles

import java.util.UUID

class ArticleService[F[_]: Concurrent] private (articles: Articles[F]) {

  def findAll: F[Seq[Article]] = articles.findAll

  def findById(id: UUID): F[Option[Article]] = articles.findById(id)

  def create(title: String, content: String, author: String): F[UUID] =
    articles.create(title, content, author)

  def update(id: UUID, columnsWithValues: Map[String, String]): F[Article] =
    articles.update(id, columnsWithValues)

  def delete(id: UUID): F[UUID] = articles.delete(id)
}

object ArticleService {
  def apply[F[_]: Concurrent](articles: Articles[F]) = new ArticleService[F](articles)
}
