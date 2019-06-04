package myob.exercise.infrastructure.encoder

import org.scalatest.{FlatSpec, Matchers}
import cats.syntax.either._

class ParserSpec extends FlatSpec with Matchers {

  import myob.exercise.infrastructure.encoder.Parsers._
  "Parser" should "parse any string into any class" in {
    case class ParserExa(name: String, value: Long)
    val cc = ParserExa("example", 2)
    Parser.parse[ParserExa]("example,2") shouldBe (cc.asRight)
  }

}
