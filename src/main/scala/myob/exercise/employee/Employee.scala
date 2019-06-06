package myob.exercise.employee

import cats.syntax.either._
import myob.exercise.EmployeeDTO

import scala.util.Try

final case class Employee(
    name: String,
    paymentRankDates: String,
    grossIncome: Int,
    incomeTax: Int,
    netIncome: Int,
    superValue: Int
)

object Employee {
  def validateAndCalculatePaySlip(
      dto: EmployeeDTO
  ): DomainError Either Employee =
    for {
      fn <- validateEmptyString("First name", dto.firstName)
      ln <- validateEmptyString("Last name", dto.lastName)
      salary <- validatePositiveInt(dto.annualSalary)
      superRate <- convertSuperRate(dto.superRate)
      grossIncome <- calculateGrossIncome(salary).asRight
      incomeTax <- calculateIncomeTax(salary).asRight
      netIncome <- calculateNetIncome(grossIncome, incomeTax).asRight
      superValue <- calculateSuperValue(grossIncome, superRate).asRight
    } yield
      Employee(
        s"$fn $ln",
        dto.paymentRankDates,
        grossIncome,
        incomeTax,
        netIncome,
        superValue
      )

  private def validatePositiveInt(salary: Int): DomainError Either Int =
    if (salary <= 0) NegativeLongValue("Annual salary is negative").asLeft
    else salary.asRight

  private def convertSuperRate(rate: String): DomainError Either Double =
    for {
      d <- deleteSuperRatePercentageSymbol(rate)
      v <- validateSuperRateRank(d)
    } yield v / 100

  private def calculateGrossIncome(annualSalary: Int): Int =
    Math.round(annualSalary / 12)

  private def calculateIncomeTax(annualSalary: Int): Int = {
    val a = if (annualSalary <= 18200) true else false
    val b = if (annualSalary > 18200) true else false
    val c = if (annualSalary <= 37000) true else false
    val d = if (annualSalary > 37000) true else false
    val e = if (annualSalary <= 87000) true else false
    val f = if (annualSalary > 87000) true else false
    val g = if (annualSalary <= 180000) true else false
    val h = if (annualSalary > 180000) true else false
    val ranks: (Boolean, Boolean, Boolean, Boolean, Boolean) =
      (a, (b && c), (d && e), (f && g), h)
    ranks match {
      case (true, false, false, false, false) => 0
      case (false, true, false, false, false) =>
        Math.round(((annualSalary - 18200) * 0.19) / 12).toInt
      case (false, false, true, false, false) =>
        Math.round((3572 + ((annualSalary - 37000) * 0.325)) / 12).toInt
      case (false, false, false, true, false) =>
        Math.round((19822 + ((annualSalary - 87000) * 0.37)) / 12).toInt
      case _ =>
        Math.round((54232 + ((annualSalary - 180000) * 0.45)) / 12).toInt
    }
  }

  private def calculateNetIncome(grossIncome: Int, incomeTax: Int): Int =
    grossIncome - incomeTax

  private def calculateSuperValue(grossIncome: Int, superRate: Double): Int =
    Math.round(grossIncome * superRate).toInt

  private def deleteSuperRatePercentageSymbol(
      s: String
  ): DomainError Either Double =
    Try(s.replace("%", "").toDouble).toEither.leftMap(
      thr =>
        InvalidRateValue(
          s"rate impossible to transform into Double due to: ${thr.toString}"
        )
    )

  private def validateSuperRateRank(
      value: Double
  ): DomainError Either Double =
    if (value < 0 || value > 50) SuperRateOutOfRank().asLeft else value.asRight

  private def validateEmptyString(
      field: String,
      value: String
  ): DomainError Either String =
    if (value.isEmpty) EmptyString(s"$field is empty").asLeft else value.asRight
}
