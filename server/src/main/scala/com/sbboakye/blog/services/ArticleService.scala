package com.sbboakye.blog.services

import cats.*
import cats.effect.*
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.repositories.Articles

import java.util.UUID

class ArticleService[F[_]: Concurrent] private (articles: Articles[F]) {

  def findAll: F[Seq[Article]] = articles.findAll

  def findById(id: UUID): F[Option[Article]] =
    articles.findById(id)

  def create(article: Article): F[UUID] =
    println("I super got here")
    articles.create(article)

  def update(id: UUID, article: Article): F[Option[Article]] =
    articles.update(id, article)

  def delete(id: UUID): F[Int] = articles.delete(id)
}

object ArticleService {
  def apply[F[_]](articles: Articles[F])(using F: Concurrent[F]): Resource[F, ArticleService[F]] =
    Resource
      .eval(F.pure(new ArticleService[F](articles)))
}
