package myob.exercise.encoder

import cats.syntax.either._
import org.scalatest.{AsyncWordSpec, Matchers}

class ParserSpec extends AsyncWordSpec with Matchers {

  import myob.exercise.encoder.Parsers._
  "Parser" should {
    case class ParserExa(name: String, value: Int)
    "parse any string into any class" in {
      val cc = ParserExa("example", 2)
      Parser.parse[ParserExa]("example,2") shouldBe cc.asRight
    }
    "return error if wasn't possible to parse the csv" in {
      Parser.parse[ParserExa](",5").isLeft should be(true)
    }
  }

}
