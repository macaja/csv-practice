package myob.exercise.encoder

import org.scalatest.{FlatSpec, Matchers}

class WriterSpec extends FlatSpec with Matchers {

  import myob.exercise.encoder.CSVs._
  import cats.instances.all._
  "Writer" should "transform any case class into a csv" in {
    case class CSVTrans(name: String, value: Int, b: Boolean)
    val ex = CSVTrans("Example", 1, true)
    CSV.to(ex) should be("Example,1,true")
  }

}
