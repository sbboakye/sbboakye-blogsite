package com.sbboakye.blog.domain.data

import java.time.OffsetDateTime
import java.util.UUID

case class Article(
    id: UUID,
    title: String,
    content: String,
    author: String,
    created_date: OffsetDateTime,
    updated_date: OffsetDateTime
)
