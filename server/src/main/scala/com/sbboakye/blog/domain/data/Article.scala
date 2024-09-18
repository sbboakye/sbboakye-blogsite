package com.sbboakye.blog.domain.data

import doobie.util.{Get, Read}
import doobie.util.meta.Meta

import java.time.LocalDateTime
import java.util.UUID

case class Article(
    id: UUID,
    title: String,
    content: String,
    author: String,
    created_date: LocalDateTime,
    updated_date: LocalDateTime
)
