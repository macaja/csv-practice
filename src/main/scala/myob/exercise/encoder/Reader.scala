package myob.exercise.encoder

import cats.syntax.either._

import scala.util.Try

trait Read[A] {
  def reads(s: String): ReadError Either A
}

trait ReadInstances {
  implicit def stringReads: Read[String] =
    Read(s => if (s.isEmpty) EmptyError().asLeft else s.asRight)

  implicit def intReads: Read[Int] =
    Read(s => Try(s.toInt).toEither.leftMap(_ => IntError()))
}

object Read extends ReadInstances {
  def apply[A](f: String => Either[ReadError, A]): Read[A] = f(_)

  def reads[A](s: String)(implicit r: Read[A]): Either[ReadError, A] = r reads s
}
