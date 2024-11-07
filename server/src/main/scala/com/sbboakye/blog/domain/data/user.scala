package com.sbboakye.blog.domain.data

import java.time.OffsetDateTime

object user {
  case class User(
      email: String,
      hashedPassword: String,
      firstName: Option[String],
      lastName: Option[String],
      role: Role,
      created_date: OffsetDateTime = OffsetDateTime.MAX,
      updated_date: OffsetDateTime = OffsetDateTime.MAX
  )

  enum Role {
    case ADMIN, READER
  }
}
