package com.sbboakye.blog.domain

import java.time.LocalDateTime

case class Blog(
    title: String,
    content: String,
    author: String,
    created_date: LocalDateTime,
    updated_date: LocalDateTime
)

object Blog {
  val dummIES: List[Blog] = List(
    Blog(
      title = "Hello World",
      content = "Some hello world blog type",
      author = "Sambeth",
      created_date = LocalDateTime.now(),
      updated_date = LocalDateTime.now()
    ),
    Blog(
      title = "Phyllisi",
      content = "WHat a wonderful world",
      author = "Sambeth",
      created_date = LocalDateTime.now(),
      updated_date = LocalDateTime.now()
    )
  )
}
