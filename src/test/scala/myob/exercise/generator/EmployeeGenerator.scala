package myob.exercise.generator

import myob.exercise.EmployeeDTO
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

object EmployeeGenerator {

  val nonEmptyString = arbitrary[String].suchThat(!_.isEmpty)

  val negativeSalary: Gen[Int] = Gen.oneOf(-100000 to -1000)
  val positiveSalary = Gen.oneOf(1000 to 200000)

  val superRateOutOfRank = for {
    v <- Gen.oneOf(51 to 100)
  } yield s"${v.toString}%"

  val validSuperRate = for {
    v <- Gen.oneOf(1 to 50)
  } yield s"${v.toString}%"

  val superRateWithoutPercentageSymbol = for {
    v <- Gen.oneOf(1 to 50)
  } yield s"${v.toString}"

  val dtoWithEmptyFirstName: Gen[EmployeeDTO] = for {
    ln <- nonEmptyString
    as <- positiveSalary
    sr <- validSuperRate
  } yield EmployeeDTO("", ln, as, sr, "01 March - 30 March")

  val dtoWithEmptyLastName: Gen[EmployeeDTO] = for {
    fn <- nonEmptyString
    as <- positiveSalary
    sr <- validSuperRate
  } yield EmployeeDTO(fn, "", as, sr, "01 March - 30 March")

  val dtoWithNegativeSalary: Gen[EmployeeDTO] = for {
    fn <- nonEmptyString
    ln <- nonEmptyString
    as <- negativeSalary
    sr <- validSuperRate
  } yield EmployeeDTO(fn, ln, as, sr, "01 March - 30 March")

  val dtoWithoutPercentageSymboltoSuperRate = for {
    fn <- nonEmptyString
    ln <- nonEmptyString
    as <- positiveSalary
    sr <- superRateWithoutPercentageSymbol
  } yield EmployeeDTO(fn, ln, as, sr, "01 March - 30 March")

  val dtoWithSuperOutOfRange = for {
    fn <- nonEmptyString
    ln <- nonEmptyString
    s <- positiveSalary
    sr <- superRateOutOfRank
  } yield EmployeeDTO(fn, ln, s, sr, "01 March - 30 March")

  val validEmployeeDTO = for {
    fn <- nonEmptyString
    ln <- nonEmptyString
    s <- positiveSalary
    sr <- validSuperRate
  } yield EmployeeDTO(fn, ln, s, sr, "01 March - 30 March")

}
