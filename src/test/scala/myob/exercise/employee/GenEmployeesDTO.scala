package myob.exercise.employee

import myob.exercise.EmployeeDTO
import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary


object GenEmployeesDTO {

  val emptyString = arbitrary[String].suchThat(_.isEmpty)
  val validString = arbitrary[String].suchThat(!_.isEmpty)

  val negativeSalary: Gen[Int] = Gen.oneOf(-100000 to -1000)
  val positiveSalary = Gen.oneOf(1000 to 200000)
  val noTaxSalary = Gen.oneOf(0 to 18200)
  val `19cTaxSalary` = Gen.oneOf(18201 to 37000)
  val  `32.5TaxSalary` = Gen.oneOf(37001 to 87000)
  val `37cTaxSalary` = Gen.oneOf(87001 to 180000)
  val `45cTaxSalary` = Gen.oneOf(180000 to 1000000)

  val superRateOutOfRank = for{
    v <- Gen.oneOf(51 to 100)
  }yield s"${v.toString}%"

  val validSuperRate = for{
    v <- Gen.oneOf(0 to 50)
  }yield s"${v.toString}%"

  val superRateWithoutPercentageSymbol = for{
    v <- Gen.oneOf(0 to 50)
  }yield s"${v.toString}"


  val dtoWithEmptyFirstName: Gen[EmployeeDTO] = for{
    fn <- emptyString
    ln <- validString
    as <- positiveSalary
    sr <- validSuperRate
  } yield EmployeeDTO(fn,ln,as,sr,"01 March - 30 March")

  val dtoWithoutPercentageSymboltoSuperRate = for{
    fn <- validString
    ln <- validString
    as <- positiveSalary
    sr <- superRateWithoutPercentageSymbol
  } yield EmployeeDTO(fn,ln,as,sr,"01 March - 30 March")

  val firstRankTaxes = for{
    fn <- emptyString
    ln <- validString
    as <- noTaxSalary
    sr <- validSuperRate
  } yield EmployeeDTO(fn,ln,as,sr,"01 March - 30 March")

  val secondRankTaxes = for{
    fn <- emptyString
    ln <- validString
    as <- `19cTaxSalary`
    sr <- validSuperRate
  } yield EmployeeDTO(fn,ln,as,sr,"01 March - 30 March")

  val thirdRankTaxes = for{
    fn <- emptyString
    ln <- validString
    as <- `32.5TaxSalary`
    sr <- validSuperRate
  } yield EmployeeDTO(fn,ln,as,sr,"01 March - 30 March")

  val fourthRankTaxes = for{
    fn <- emptyString
    ln <- validString
    as <- `37cTaxSalary`
    sr <- validSuperRate
  } yield EmployeeDTO(fn,ln,as,sr,"01 March - 30 March")

  val thirdRankTaxes = for{
    fn <- emptyString
    ln <- validString
    as <- `45cTaxSalary`
    sr <- validSuperRate
  } yield EmployeeDTO(fn,ln,as,sr,"01 March - 30 March")


}
