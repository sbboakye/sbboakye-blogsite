package com.sbboakye.blog.repositories

import cats.*
import cats.effect.*
import cats.effect.MonadCancelThrow
import com.sbboakye.blog.core.Users
import com.sbboakye.blog.domain.data.user
import doobie.Transactor
import org.typelevel.log4cats.Logger

class UsersRepository[F[_]: MonadCancelThrow: Logger] private (xa: Transactor[F]) extends Users[F] {
  override def findAll: F[Seq[user.User]] = ???

  override def findById(email: String): F[Option[user.User]] = ???

  override def create(article: user.User): F[String] = ???

  override def update(email: String, article: user.User): F[Option[user.User]] = ???

  override def delete(email: String): F[Option[Int]] = ???
}

object UsersRepository {
  def apply[F[_]: MonadCancelThrow: Logger](
      xa: Transactor[F]
  )(using F: Async[F]): Resource[F, Users[F]] =
    Resource
      .eval(F.pure(new UsersRepository[F](xa)))
}
