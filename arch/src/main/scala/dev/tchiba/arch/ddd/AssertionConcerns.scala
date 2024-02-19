package dev.tchiba.arch.ddd

trait AssertionConcerns {

  protected def requires[T](fs: T => Either[String, Unit]*)(value: T): Unit = {
    invariant(fs.map(_(value)): _*)
  }

  protected def invariant(es: Either[String, Unit]*): Unit = {
    es.fold(Right()) { (e1, e2) =>
      for {
        _ <- e1
        _ <- e2
      } yield ()
    }.left.foreach(e => throw new IllegalArgumentException("requirement failed: " + e))
  }
}
