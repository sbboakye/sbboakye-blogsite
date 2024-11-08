package com.sbboakye.blog.repositories

import cats.*
import cats.effect.*
import cats.effect.MonadCancelThrow
import com.sbboakye.blog.core.Articles
import doobie.*
import doobie.implicits.*
import doobie.postgres.*
import doobie.postgres.implicits.*
import com.sbboakye.blog.domain.data.Article
import org.typelevel.log4cats.Logger

import java.util.UUID

class ArticlesRepository[F[_]: MonadCancelThrow: Logger] private (xa: Transactor[F])
    extends Articles[F] {
  private val select =
    fr"SELECT id, title, content, author, created_date, updated_date FROM articles"

  private def where(id: UUID): Fragment = fr"WHERE id = $id"

  def findAll: F[Seq[Article]] =
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

  override def create(article: Article): F[UUID] =
    sql"""INSERT INTO articles (title, content, author)
         VALUES (${article.title}, ${article.content}, ${article.author})""".update
      .withUniqueGeneratedKeys[UUID]("id")
      .transact(xa)

  override def update(id: UUID, article: Article): F[Option[Int]] =
    val update  = fr"UPDATE articles"
    val title   = fr"title = ${article.title}"
    val content = fr"content = ${article.content}"
    val setter  = fr"SET " ++ title ++ fr", " ++ content

    val fullUpdate = update ++ setter ++ where(id)
    fullUpdate.update.run
      .map {
        case 0 => None
        case n => Some(n)
      }
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
  def apply[F[_]: MonadCancelThrow: Logger](
      xa: Transactor[F]
  )(using F: Async[F]): Resource[F, Articles[F]] =
    Resource
      .eval(F.pure(new ArticlesRepository[F](xa)))
}
