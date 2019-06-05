package myob.exercise.employee

import org.scalatest.{AsyncWordSpec, Matchers}
import org.scalacheck.Prop.forAll

class EmployeeSpec extends AsyncWordSpec with Matchers {

  "Employees" should {
    "return a Domain Error if first name is empty" in {
      check{

      }
    }
  }

}
