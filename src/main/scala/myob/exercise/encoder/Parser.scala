package myob.exercise.encoder

import cats.syntax.either._
import shapeless.{::, HList, HNil, ProductTypeClass, ProductTypeClassCompanion}

trait Parser[A] {
  def parse: String => ReadError Either A
}

object Parser {
  def parse[A](s: String)(implicit p: Parser[A]): Either[ReadError, A] =
    p parse s

  def apply[A](f: String => Either[ReadError, A]): Parser[A] = new Parser[A] {
    override def parse: String => Either[ReadError, A] = f
  }
}

object Parsers extends ProductTypeClassCompanion[Parser] {
  override val typeClass: ProductTypeClass[Parser] =
    new ProductTypeClass[Parser] {
      override def product[H, T <: HList](
          ch: Parser[H],
          ct: Parser[T]
      ): Parser[H :: T] =
        Parser[H :: T](_.trim.split(",").toList match {
          case h +: t =>
            for {
              head <- ch.parse(h)
              tail <- ct.parse(t.mkString(","))
            } yield head :: tail
          case _ => EmptyError().asLeft
        })

      override def project[F, G](
          instance: => Parser[G],
          to: F => G,
          from: G => F
      ): Parser[F] = Parser[F](instance.parse(_) map from)

      override def emptyProduct: Parser[HNil] =
        Parser[HNil](s => if (s.isEmpty) HNil.asRight else EmptyError().asLeft)
    }

  implicit def readParser[A](implicit r: Read[A]): Parser[A] =
    Parser[A](r.reads)
}
