package com.sbboakye.blog.controllers

import scalatags.Text
import scalatags.Text.all.*
import scalatags.Text.tags2.title as mainTitle

object BlogViews {

  class Parent {
    def render: Text.TypedTag[String] = html(headFrag, bodyFrag)

    private def headFrag: Text.TypedTag[String] = head(
      script("some script"),
      mainTitle("Hello World"),
      link(
        href := "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css",
        rel  := "stylesheet"
      )
    )

    private def bodyFrag: Text.TypedTag[String] = body(
      h1("This is my simple title"),
      div(
        p("This is my first paragraph"),
        p("This is my second paragraph")
      )
    )
  }

  object Child extends Parent

}
