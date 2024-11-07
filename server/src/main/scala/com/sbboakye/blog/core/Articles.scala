package com.sbboakye.blog.core

import doobie.util.transactor.Transactor
import cats.*
import cats.effect.*
import cats.effect.MonadCancelThrow
import com.sbboakye.blog.domain.data.Article
import com.sbboakye.blog.repositories.ArticlesRepository
import org.typelevel.log4cats.Logger

import java.util.UUID

trait Articles[F[_]]:
  def findAll: F[Seq[Article]]
  def findById(id: UUID): F[Option[Article]]
  def create(article: Article): F[UUID]
  def update(id: UUID, article: Article): F[Option[Article]]
  def delete(id: UUID): F[Option[Int]]
