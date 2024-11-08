package com.sbboakye.blog.core

import cats.*
import cats.effect.*
import cats.effect.testing.scalatest.AsyncIOSpec
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.fixtures.ArticleFixture
import com.sbboakye.blog.repositories.ArticlesRepository
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

class ArticlesSpec
    extends AsyncFreeSpec
    with AsyncIOSpec
    with Matchers
    with CoreSpec
    with ArticleFixture {

  override val initSqlScript: String = "sql/articles.sql"

  given logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  "Articles algebra" - {
    "return no article if the given uuid does not exist" in {
      transactor.use { xa =>
        val program = for {
          articles  <- ArticlesRepository[IO](xa)
          retrieved <- articles.findById(notFoundArticleUuid).toResource
        } yield retrieved
        program.asserting(_ shouldBe None).use(IO.pure)
      }
    }

    "return 2 articles if all articles are listed" in {
      transactor.use { xa =>
        val program = for {
          articles  <- ArticlesRepository[IO](xa)
          retrieved <- articles.findAll.toResource
        } yield retrieved
        program.asserting(_.length shouldBe 2).use(IO.pure)
      }
    }

    "return 1 if article updated" in {
      transactor.use { xa =>
        val program = for {
          articles  <- ArticlesRepository[IO](xa)
          retrieved <- articles.update(articleUuid, updatedArticle).toResource
        } yield retrieved
        program.asserting(_ shouldBe Some(1)).use(IO.pure)
      }
    }

    "return none if article to be updated does not exist" in {
      transactor.use { xa =>
        val program = for {
          articles  <- ArticlesRepository[IO](xa)
          retrieved <- articles.update(notFoundArticleUuid, updatedArticle).toResource
        } yield retrieved
        program.asserting(_ shouldBe None).use(IO.pure)
      }
    }

    "return 1 if article is deleted" in {
      transactor.use { xa =>
        val program = for {
          articles  <- ArticlesRepository[IO](xa)
          retrieved <- articles.delete(articleUuid).toResource
        } yield retrieved
        program.asserting(_ shouldBe Some(1)).use(IO.pure)
      }
    }

    "return None if article to be deleted does not exist" in {
      transactor.use { xa =>
        val program = for {
          articles  <- ArticlesRepository[IO](xa)
          retrieved <- articles.delete(notFoundArticleUuid).toResource
        } yield retrieved
        program.asserting(_ shouldBe None).use(IO.pure)
      }
    }
  }
}
