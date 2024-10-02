package com.sbboakye.blog.services

import java.util.UUID

import cats.effect.*
import cats.implicits.*
import cats.effect.testing.scalatest.AsyncIOSpec

import io.circe.generic.auto.*
import org.http4s.circe.CirceEntityCodec.*
import org.http4s.dsl.Http4sDsl
import org.http4s.*
import org.http4s.implicits.*

import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import com.sbboakye.blog.fixtures.ArticleFixture
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.repositories.Articles

class ArticleRoutesSpec
    extends AsyncFreeSpec
    with AsyncIOSpec
    with Matchers
    with Http4sDsl[IO]
    with ArticleFixture {

  /*
  create mock articles repository
   */
  val articlesRepo: Articles[IO] = new Articles[IO]:
    override def findAll: IO[Seq[Article]] = IO.pure(Seq(article))

    override def findById(id: UUID): IO[Option[Article]] =
      println("Did you come back here")
      if (id == articleUuid) then IO.pure(Some(article))
      else IO.pure(None)

    override def create(article: Article): IO[UUID] = IO.pure(newArticleUuid)

    override def update(id: UUID, article: Article): IO[Option[Article]] =
      if (id == articleUuid) then IO.pure(Some(article))
      else IO.pure(None)

    override def delete(id: UUID): IO[Int] =
      if (id == articleUuid) then IO.pure(1)
      else IO.pure(0)

  given logger: Logger[IO]          = Slf4jLogger.getLogger[IO]
  val articleRoutes: HttpRoutes[IO] = ArticleRoutes[IO](articles).routes

  // testing begins from here
  "ArticleRoutes" - {
    "should return an article with a given id" in {
      for {
        response <- articleRoutes.orNotFound.run(
          Request(method = Method.GET, uri = uri"/843df718-ec6e-4d49-9289-f799c0f40064")
        )
        _         <- logger.info(s"Response: $response")
        retrieved <- response.as[Article]
      } yield {
        response.status shouldBe Status.Ok
        retrieved shouldBe article
      }
    }
  }
}
