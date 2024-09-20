package com.sbboakye.blog.views

import cats.*
import cats.effect.*
import cats.syntax.all.*
import scalatags.Text.TypedTag
import scalatags.Text.all.*
import scalatags.Text.tags2.title as mainTitle

object HomeView {

  class Home[F[_]](using F: Concurrent[F]) {
    val bodyContents: F[TypedTag[String]] = F.pure(div())

    def render: F[TypedTag[String]] =
      for {
        hFrag <- headFrag
        bFrag <- bodyFrag
      } yield html(hFrag, bFrag)

    private def headFrag: F[TypedTag[String]] = F.pure(
      head(
        script("some script"),
        mainTitle("Hello World"),
        link(
          href := "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css",
          rel  := "stylesheet"
        ),
        script(src := "https://unpkg.com/htmx.org@2.0.2")
      )
    )

    private def bodyFrag: F[TypedTag[String]] =
      bodyContents.map { bc =>
        body(
          `class` := "container",
          div(id := "div-body", bc)
        )
      }
  }

}
