package com.sbboakye.blog.views.htmx

import scalatags.Text.all.*
import scalatags.generic
import scalatags.text.Builder

object HtmxAttributes {
  def get(endpoint: String): generic.AttrPair[Builder, String]    = attr("hx-get")    := endpoint
  def post(endpoint: String): generic.AttrPair[Builder, String]   = attr("hx-post")   := endpoint
  def put(endpoint: String): generic.AttrPair[Builder, String]    = attr("hx-put")    := endpoint
  def patch(endpoint: String): generic.AttrPair[Builder, String]  = attr("hx-patch")  := endpoint
  def delete(endpoint: String): generic.AttrPair[Builder, String] = attr("hx-delete") := endpoint

  def target(element: String): generic.AttrPair[Builder, String] = attr("hx-target")  := element
  def trigger(value: String): generic.AttrPair[Builder, String]  = attr("hx-trigger") := value
  def include(value: String): generic.AttrPair[Builder, String]  = attr("hx-include") := value
  def headers(value: String): generic.AttrPair[Builder, String]  = attr("hx-headers") := value
}
