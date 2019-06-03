package myob.exercise.infrastructure.encoder

import cats.syntax.either._

import scala.util.Try

trait Read[A] {
  def reads(s: String): ReadError Either A
}

trait ReadInstances {
  implicit def stringReads: Read[String] =
    Read(s => if (s.isEmpty) EmptyError().asLeft else s.asRight)

  implicit def longReads: Read[Long] =
    Read(s => Try(s.toLong).toEither.leftMap(_ => LongError()))
}

object Read extends ReadInstances {
  def apply[A](f: (String) => Either[ReadError, A]): Read[A] = f(_)

  def reads[A](s: String)(implicit r: Read[A]): Either[ReadError, A] = r reads s
}

trait ReadError {
  def msg: String
}

final case class EmptyError(msg: String = "Field Empty") extends ReadError
final case class LongError(msg: String = "Long error") extends ReadError
