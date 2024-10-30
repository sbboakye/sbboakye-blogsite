package com.sbboakye.blog.repositories

import cats.*
import cats.effect.*
import cats.effect.MonadCancelThrow

import doobie.*
import doobie.implicits.*
import doobie.postgres.*
import doobie.postgres.implicits.*

import com.sbboakye.blog.domain.data.Article

import java.util.UUID

class ArticlesRepository[F[_]: MonadCancelThrow] private (xa: Transactor[F]) extends Articles[F] {
  private val select =
    fr"SELECT id, title, content, author, created_date, updated_date FROM articles"

  private def where(id: UUID): Fragment = fr"WHERE id = $id"

  def findAll: F[Seq[Article]] =
    select
      .query[Article]
      .to[Seq]
      .transact(xa)

  override def findById(id: UUID): F[Option[Article]] =
    println("I am in article repository instead")
    val fullQuery = select ++ fr"WHERE id = $id"
    fullQuery
      .query[Article]
      .option
      .transact(xa)

  override def create(article: Article): F[UUID] =
    sql"""INSERT INTO articles (title, content, author)
         VALUES (${article.title}, ${article.content}, ${article.author})""".update
      .withUniqueGeneratedKeys[UUID]("id")
      .transact(xa)

  override def update(id: UUID, article: Article): F[Option[Article]] =
    val update  = fr"UPDATE articles"
    val title   = fr"title = ${article.title}"
    val content = fr"content = ${article.content}"
    val setter  = fr"SET " ++ title ++ fr", " ++ content

    val fullUpdate = update ++ setter ++ where(id)
    fullUpdate.update
      .withUniqueGeneratedKeys[Option[Article]](
        "id",
        "title",
        "content",
        "author",
        "created_date",
        "updated_date"
      )
      .transact(xa)

  override def delete(id: UUID): F[Option[Int]] =
    val remove    = fr"DELETE FROM articles"
    val fullQuery = remove ++ where(id)

    fullQuery.update.run
      .map {
        case 0 => None
        case n => Some(n)
      }
      .transact(xa)
}

object ArticlesRepository {
  def apply[F[_]: MonadCancelThrow](xa: Transactor[F]) = new ArticlesRepository[F](xa)
}
