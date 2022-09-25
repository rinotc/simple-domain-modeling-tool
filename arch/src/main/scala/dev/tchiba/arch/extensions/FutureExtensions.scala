package dev.tchiba.arch.extensions

import scala.concurrent.Future

trait FutureExtensions {
  implicit class FutureOps[A](value: A) {
    def future: Future[A] = Future.successful(value)
  }
}
