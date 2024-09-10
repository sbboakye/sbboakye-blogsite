package com.sbboakye.blog.domain

import java.time.LocalDateTime
import java.util.UUID

case class Blog(
    id: UUID = UUID.randomUUID(),
    title: String,
    content: String,
    author: String = "Sambeth",
    created_date: LocalDateTime = LocalDateTime.now(),
    updated_date: LocalDateTime = LocalDateTime.now()
)

object Blog {
  val seed: Seq[Blog] = Seq(
    Blog(
      title = "Hello World",
      content = "Some hello world blog type"
    ),
    Blog(
      title = "Phyllisi",
      content = "WHat a wonderful world"
    )
  )
}
