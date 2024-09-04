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
  val dummy: Blog = Blog(
    "Hello World",
    "Some hello world blog type",
    "Sambeth",
    LocalDateTime.now(),
    LocalDateTime.now()
  )
}
