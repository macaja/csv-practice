package myob.exercise.employee

import cats.syntax.either._
import myob.exercise.infrastructure.file.EmployeeDTO

import scala.util.Try

case class Employee(
    name: String,
    paymentRankDates: String,
    grossIncome: Long,
    incomeTax: Long,
    netIncome: Long,
    superValue: Long
)

object Employee {
  def validate(dto: EmployeeDTO): DomainError Either Employee =
    for {
      salary <- validatePositiveLong(dto.annualSalary)
      superRate <- convertSuperRate(dto.superRate)
      grossIncome <- calculateGrossIncome(salary).asRight
      incomeTax <- calculateIncomeTax(salary).asRight
      netIncome <- calculateNetIncome(grossIncome, incomeTax).asRight
      superValue <- calculateSuperValue(grossIncome, superRate).asRight
    } yield
      Employee(
        s"${dto.firstName} ${dto.lastName}",
        dto.paymentRankDates,
        grossIncome,
        incomeTax,
        netIncome,
        superValue
      )

  private def validatePositiveLong(salary: Long): DomainError Either Long =
    if (salary <= 0) NegativeLongValue("Annual salary is negative").asLeft
    else (salary).asRight

  private def convertSuperRate(rate: String): DomainError Either Double =
    for {
      d <- deleteSuperRatePercentageSymbol(rate)
      v <- validateSuperRateRank(d)
    } yield v / 100

  private def calculateGrossIncome(annualSalary: Long): Long =
    Math.round(annualSalary / 12)

  private def calculateIncomeTax(annualSalary: Long): Long =
    annualSalary match {
      case x if (x <= 18200) => 0
      case x if (x > 18200 && x <= 37000) =>
        Math.round(((annualSalary - 18200) * 0.19) / 12)
      case x if (x > 37000 && x <= 87000) =>
        Math.round((3572 + ((annualSalary - 37000) * 0.325)) / 12)
      case x if (x > 87000 && x <= 180000) =>
        Math.round((19822 + ((annualSalary - 87000) * 0.37)) / 12)
      case x if (x > 180000) =>
        Math.round((54232 + ((annualSalary - 180000) * 0.45)) / 12)
    }

  private def calculateNetIncome(grossIncome: Long, incomeTax: Long): Long =
    grossIncome - incomeTax

  private def calculateSuperValue(grossIncome: Long, superRate: Double): Long =
    Math.round(grossIncome * superRate)

  private[this] def deleteSuperRatePercentageSymbol(
      s: String
  ): DomainError Either Double =
    Try(s.replace("%", "").toDouble).toEither.leftMap(
      thr =>
        InvalidRateValue(
          s"rate impossible to transform into Double due to: ${thr.toString}"
        )
    )

  private[this] def validateSuperRateRank(
      value: Double
  ): DomainError Either Double =
    if (value < 0 || value > 100) SuperRateOutOfRank().asLeft else value.asRight

}
