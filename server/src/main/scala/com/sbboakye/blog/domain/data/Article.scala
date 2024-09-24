package com.sbboakye.blog.domain.data

import java.time.OffsetDateTime
import java.util.UUID

case class Article(
    id: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000"),
    title: String,
    content: String,
    author: String = "sambeth",
    created_date: OffsetDateTime = OffsetDateTime.MAX,
    updated_date: OffsetDateTime = OffsetDateTime.MAX
)
