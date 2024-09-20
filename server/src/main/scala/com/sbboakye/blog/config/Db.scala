package com.sbboakye.blog.config

import doobie.util.transactor.Transactor
import cats.*
import cats.effect.*
import com.sbboakye.blog.repositories.*

final class Db[F[_]] private (val articles: Articles[F])

object Db {

  def apply[F[_]](transactor: Transactor[F])(using F: Async[F]): Resource[F, Articles[F]] =
    Resource
      .eval(F.pure(ArticlesRepository[F](transactor)))

}
