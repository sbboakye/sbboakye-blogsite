//package com.sbboakye.blog.config
//
//import doobie.util.transactor.Transactor
//import cats.*
//import cats.effect.*
//import com.sbboakye.blog.core.Articles
//import com.sbboakye.blog.repositories.*
//import org.typelevel.log4cats.Logger
//
//final class Db[F[_]: MonadCancelThrow: Logger] private (val articles: Articles[F])
//
//object Db {
//
//  def apply[F[_]: MonadCancelThrow: Logger](
//      transactor: Transactor[F]
//  )(using F: Async[F]): Resource[F, Articles[F]] =
//    Resource
//      .eval(F.pure(ArticlesRepository[F](transactor)))
//
//}
