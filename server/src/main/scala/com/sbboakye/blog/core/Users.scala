package com.sbboakye.blog.core

import com.sbboakye.blog.domain.data.user.User

trait Users[F[_]]:
  def findAll: F[Seq[User]]

  def findById(email: String): F[Option[User]]

  def create(article: User): F[String]

  def update(email: String, article: User): F[Option[User]]

  def delete(email: String): F[Option[Int]]
