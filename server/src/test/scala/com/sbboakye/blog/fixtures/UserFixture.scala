package com.sbboakye.blog.fixtures

import com.sbboakye.blog.domain.data.user.Role.{ADMIN, READER}
import com.sbboakye.blog.domain.data.user.User

trait UserFixture {

  val samuel: User = User(
    "samuelbaafiboakye@gmail.com",
    "reallLige",
    Option("Samuel"),
    Option("Boakye"),
    ADMIN
  )

  val sambeth: User = User(
    "sambethslim@gmail.com",
    "214fasdsg",
    Option("Sam"),
    Option("Beth"),
    READER
  )

}
