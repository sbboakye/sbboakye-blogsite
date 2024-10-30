package com.sbboakye.blog.domain.data

import java.time.OffsetDateTime
import java.util.UUID

case class Article(
    id: UUID = UUID.randomUUID(),
    title: String,
    content: String,
    author: String,
    created_date: OffsetDateTime = OffsetDateTime.MAX,
    updated_date: OffsetDateTime = OffsetDateTime.MAX
)
