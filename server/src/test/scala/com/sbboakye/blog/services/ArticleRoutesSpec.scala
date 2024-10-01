package com.sbboakye.blog.services

import cats.effect.*
import cats.implicits.*
import com.sbboakye.blog.fixtures.ArticleFixture
import org.http4s.dsl.Http4sDsl
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import cats.effect.testing.scalatest.AsyncIOSpec
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.repositories.Articles
import org.http4s.HttpRoutes
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

import java.util.UUID

class ArticleRoutesSpec
    extends AsyncFreeSpec
    with AsyncIOSpec
    with Matchers
    with Http4sDsl[IO]
    with ArticleFixture {

  val articles: Articles[IO] = new Articles[IO]:
    override def findAll: IO[Seq[Article]] = IO.pure(Seq(article))

    override def findById(id: UUID): IO[Option[Article]] =
      if (id == articleUuid) then IO.pure(Some(article))
      else IO.pure(None)

    override def create(article: Article): IO[UUID] = IO.pure(newArticleUuid)

    override def update(id: UUID, article: Article): IO[Option[Article]] =
      if (id == articleUuid) then IO.pure(Some(article))
      else IO.pure(None)

    override def delete(id: UUID): IO[Int] =
      if (id == articleUuid) then IO.pure(1)
      else IO.pure(0)

    given logger: Logger[IO]           = Slf4jLogger.getLogger[IO]
    val articlesRoutes: HttpRoutes[IO] = ArticleRoutes[IO](articles).routes

    // testing begins from here

}
