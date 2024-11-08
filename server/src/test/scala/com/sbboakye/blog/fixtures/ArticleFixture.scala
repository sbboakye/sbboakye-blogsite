package com.sbboakye.blog.fixtures

import com.sbboakye.blog.domain.data.*

import java.util.UUID

trait ArticleFixture {
  val notFoundArticleUuid: UUID = UUID.fromString("6ea79557-3112-4c84-a8f5-1d1e2c300948")
  val articleUuid: UUID         = UUID.fromString("843df718-ec6e-4d49-9289-f799c0f40064")

  val article: Article = Article(
    articleUuid,
    "Phyllis",
    "Phyllis",
    "Sambeth"
  )

  val invalidArticle: Article = Article(
    null,
    "Hello",
    "World",
    "Sambeth"
  )

  val updatedArticle: Article = Article(
    articleUuid,
    "Hello",
    "World",
    "Sambeth"
  )

  val articleWithNotFoundId: Article = article.copy(id = notFoundArticleUuid)

  val anotherArticleUuid: UUID = UUID.fromString("19a941d0-aa19-477b-9ab0-a7033ae65c2b")
  val anotherArticle: Article  = article.copy(id = anotherArticleUuid)

  val newArticleUuid: UUID = UUID.fromString("efcd2a64-4463-453a-ada8-b1bae1db4377")
  val newArticle: Article = Article(
    newArticleUuid,
    "Hello",
    "World",
    "Sambeth"
  )
}
