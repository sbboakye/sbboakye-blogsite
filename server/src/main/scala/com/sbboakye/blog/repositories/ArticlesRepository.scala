package com.sbboakye.blog.repositories

import doobie.*
import doobie.implicits.*
import doobie.postgres.*
import doobie.postgres.implicits.*

import cats.*
import cats.data.*
import cats.effect.*
import cats.implicits.*

import com.sbboakye.blog.domain.data.Article
import java.util.UUID

trait ArticlesRepository[F[_]]:
  def findAll: F[Seq[Article]]
  def findById(id: UUID): F[Option[Article]]
  def create(title: String, content: String, author: String): F[UUID]
  def update(id: UUID, columnsWithValues: Map[String, String]): F[Article]
  def delete(id: UUID): F[UUID]

object ArticlesRepository {
  private val select =
    fr"SELECT id, title, content, author, created_date, updated_date FROM articles"

  private def where(id: UUID) = fr"WHERE id = $id"

  def make[F[_]: MonadCancelThrow](xa: Transactor[F]): ArticlesRepository[F] = {
    new ArticlesRepository[F]:
      override def findAll: F[Seq[Article]] =
        select
          .query[Article]
          .to[Seq]
          .transact(xa)

      override def findById(id: UUID): F[Option[Article]] =
        val fullQuery = select ++ fr"WHERE id = $id"
        fullQuery
          .query[Article]
          .option
          .transact(xa)

      override def create(title: String, content: String, author: String): F[UUID] =
        sql"INSERT INTO articles (title, content, author) VALUES ($title, $content, $author)".update
          .withUniqueGeneratedKeys[UUID]("id")
          .transact(xa)

      override def update(id: UUID, columnsWithValues: Map[String, String]): F[Article] =
        val update = fr"UPDATE articles"
        val setter = fr"SET ${columnsWithValues.map { (k, v) => k + " = " + v }.mkString(",")}"

        val fullQuery = update ++ setter ++ where(id)

        fullQuery.update
          .withUniqueGeneratedKeys[Article](
            "id",
            "title",
            "content",
            "author",
            "created_date",
            "updated_date"
          )
          .transact(xa)

      override def delete(id: UUID): F[UUID] =
        val remove    = fr"DELETE FROM articles"
        val fullQuery = remove ++ where(id)

        fullQuery.update
          .withUniqueGeneratedKeys[UUID]("id")
          .transact(xa)
  }

}
