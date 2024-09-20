package com.sbboakye.blog.config

import doobie.util.log.LogEvent

trait LogHandler[M[_]]:
  def run(logEvent: LogEvent): M[Unit]
