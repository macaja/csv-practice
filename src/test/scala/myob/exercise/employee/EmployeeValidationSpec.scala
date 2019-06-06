package myob.exercise.employee

import myob.exercise.EmployeeDTO
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

class EmployeeValidationSpec extends Properties("Employee") {
  import myob.exercise.generator.EmployeeGenerator._

  property("Employee validation success") = forAll { validEmployeeDTO } {
    dto: EmployeeDTO =>
      Employee
        .validateAndCalculatePaySlip(
          dto
        )
        .isRight
  }

  property("Employee validation failed due to first name empty") = forAll {
    dtoWithEmptyFirstName
  } { dto: EmployeeDTO =>
    Employee.validateAndCalculatePaySlip(dto).isLeft
  }

  property("Employee validation failed due to last name empty") = forAll {
    dtoWithEmptyLastName
  } { dto: EmployeeDTO =>
    Employee.validateAndCalculatePaySlip(dto).isLeft
  }

  property("Employee validation failed due to negative annual salary") =
    forAll {
      dtoWithNegativeSalary
    } { dto: EmployeeDTO =>
      Employee.validateAndCalculatePaySlip(dto).isLeft
    }

}
