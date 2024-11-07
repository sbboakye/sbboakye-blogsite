package com.sbboakye.blog.core

import com.dimafeng.testcontainers.{ContainerDef, JdbcDatabaseContainer, PostgreSQLContainer}
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import cats.effect.*
import doobie.*
import doobie.hikari.HikariTransactor
import doobie.implicits.*
import doobie.util.*
import org.testcontainers.utility.DockerImageName

trait CoreSpec {

  val initSqlScript: String

  val postgres: Resource[IO, PostgreSQLContainer] = {
    val acquire = IO {
      val container = PostgreSQLContainer.Def(
        dockerImageName = DockerImageName.parse("postgres"),
        databaseName = "testcontainer-scala",
        username = "scala",
        password = "scala",
        commonJdbcParams =
          JdbcDatabaseContainer.CommonParams(initScriptPath = Option(initSqlScript))
      )
      container.start()
    }
    val release = (container: PostgreSQLContainer) => IO(container.close())
    Resource.make(acquire)(release)
  }

  val transactor: Resource[IO, Transactor[IO]] = for {
    db <- postgres
    ce <- ExecutionContexts.fixedThreadPool[IO](1)
    xa <- HikariTransactor.newHikariTransactor[IO](
      "org.postgresql.Driver",
      db.jdbcUrl,
      db.username,
      db.password,
      ce
    )
  } yield xa

}
