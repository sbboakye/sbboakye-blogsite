package com.sbboakye.blog.domain

import java.time.LocalDateTime
import java.util.UUID

case class Blog(
    id: UUID,
    title: String,
    content: String,
    author: String,
    created_date: LocalDateTime,
    updated_date: LocalDateTime
)

object Blog {
  val dummies: List[Blog] = List(
    Blog(
      id = UUID.randomUUID(),
      title = "Hello World",
      content = "Some hello world blog type",
      author = "Sambeth",
      created_date = LocalDateTime.now(),
      updated_date = LocalDateTime.now()
    ),
    Blog(
      id = UUID.randomUUID(),
      title = "Phyllisi",
      content = "WHat a wonderful world",
      author = "Sambeth",
      created_date = LocalDateTime.now(),
      updated_date = LocalDateTime.now()
    )
  )
}
