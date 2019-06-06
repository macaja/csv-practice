package myob.exercise.employee

import myob.exercise.EmployeeDTO
import org.scalatest.{AsyncWordSpec, Matchers}

class EmployeeCalculationSpec extends AsyncWordSpec with Matchers {
  import EmployeeCalculationSpec._

  "Employee" should {
    "calculate deductions and monthly pay slip when gross income doesn't exceed 18200 a year" in {
      Employee
        .validateAndCalculatePaySlip(dtoBase.copy(annualSalary = 10000))
        .fold(
          _ => succeed,
          _ should be(
            baseAnswer.copy(
              grossIncome = 833,
              incomeTax = 0,
              netIncome = 833,
              superValue = 125
            )
          )
        )
    }
    "calculate deductions and monthly pay slip when gross income is between 18201 and 37000 a year" in {
      Employee
        .validateAndCalculatePaySlip(dtoBase.copy(annualSalary = 22000))
        .fold(
          _ => succeed,
          _ should be(
            baseAnswer.copy(
              grossIncome = 1833,
              incomeTax = 60,
              netIncome = 1773,
              superValue = 275
            )
          )
        )
    }
    "calculate deductions and monthly pay slip when gross income is between 37001 and 87000 a year" in {
      Employee
        .validateAndCalculatePaySlip(dtoBase.copy(annualSalary = 50000))
        .fold(
          _ => succeed,
          _ should be(
            baseAnswer.copy(
              grossIncome = 4166,
              incomeTax = 650,
              netIncome = 3516,
              superValue = 625
            )
          )
        )
    }
    "calculate deductions and monthly pay slip when gross income is between 87001 and 180000 a year" in {
      Employee
        .validateAndCalculatePaySlip(dtoBase.copy(annualSalary = 120000))
        .fold(
          _ => succeed,
          _ should be(
            baseAnswer.copy(
              grossIncome = 10000,
              incomeTax = 2669,
              netIncome = 7331,
              superValue = 1500
            )
          )
        )
    }
    "calculate deductions and monthly pay slip when gross income is over 180001 a year" in {
      Employee
        .validateAndCalculatePaySlip(dtoBase.copy(annualSalary = 200000))
        .fold(
          _ => succeed,
          _ should be(
            baseAnswer.copy(
              grossIncome = 16666,
              incomeTax = 5269,
              netIncome = 11397,
              superValue = 2500
            )
          )
        )
    }
  }
}

object EmployeeCalculationSpec {
  val dtoBase = EmployeeDTO(
    "Daniel",
    "Hurt",
    10000,
    "15%",
    "01 January - 30 January"
  )

  val baseAnswer = Employee(
    "Daniel Hurt",
    "01 January - 30 January",
    0,
    0,
    0,
    0
  )
}
