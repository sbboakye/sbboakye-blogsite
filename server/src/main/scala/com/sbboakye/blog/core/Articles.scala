package com.sbboakye.blog.core

import com.sbboakye.blog.domain.data.Article

import java.util.UUID

trait Articles[F[_]]:
  def findAll: F[Seq[Article]]
  def findById(id: UUID): F[Option[Article]]
  def create(article: Article): F[UUID]
  def update(id: UUID, article: Article): F[Option[Article]]
  def delete(id: UUID): F[Option[Int]]
