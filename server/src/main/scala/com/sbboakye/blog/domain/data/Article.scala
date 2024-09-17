package com.sbboakye.blog.domain.data

import java.time.LocalDateTime
import java.util.UUID

case class Article(
    id: UUID = UUID.randomUUID(),
    title: String,
    content: String,
    author: String = "Sambeth",
    created_date: LocalDateTime = LocalDateTime.now(),
    updated_date: LocalDateTime = LocalDateTime.now()
)
