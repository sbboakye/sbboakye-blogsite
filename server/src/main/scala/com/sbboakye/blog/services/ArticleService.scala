package com.sbboakye.blog.services

import cats.effect.*
import cats.*
import cats.data.*
import cats.effect.*
import cats.implicits.*
import com.sbboakye.blog.db.Db
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.repositories.ArticlesRepository
import doobie.hikari.HikariTransactor

import java.util.UUID

trait ArticleService[F[_]]:
  def findAll: F[Seq[Article]]
  def findById(id: UUID): F[Option[Article]]
  def create(title: String, content: String, author: String): F[UUID]
  def update(id: UUID, columnsWithValues: Map[String, String]): F[Article]
  def delete(id: UUID): F[UUID]

object ArticleService {
  def apply[F[_]](using F: Async[F]): ArticleService[F] = new ArticleService[F]:

    val dbTransactor: F[HikariTransactor[F]] = for {
      resource   <- Db.createTransactor[F]
      transactor <- resource.use { xa => F.pure(xa) }
    } yield transactor

    override def findAll: F[Seq[Article]] = ???

    override def findById(id: UUID): F[Option[Article]] = ???

    override def create(title: String, content: String, author: String): F[UUID] = ???

    override def update(id: UUID, columnsWithValues: Map[String, String]): F[Article] = ???

    override def delete(id: UUID): F[UUID] = ???
}
