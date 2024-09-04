package com.sbboakye.blog.views

import scalatags.Text.TypedTag
import scalatags.Text.all.*
import scalatags.Text.tags2.title as mainTitle

object HomePage {

  class Home {
    def render: TypedTag[String] = html(headFrag, bodyFrag())

    private def headFrag: TypedTag[String] = head(
      script("some script"),
      mainTitle("Hello World"),
      link(
        href := "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css",
        rel  := "stylesheet"
      )
    )

    private def bodyFrag(bodyContents: List[TypedTag[String]] = List.empty): TypedTag[String] =
      body(
        `class` := "container",
        div(
          bodyContents
        )
      )
  }

}
