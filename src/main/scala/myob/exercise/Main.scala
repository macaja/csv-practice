package myob.exercise
import os.Path

object Main extends App with FileProcessManagement {

  val wd: Path = os.pwd / "src" / "main" / "resources"

  processGrossEmployees(wd, "employeesGrossIncome.csv")
}
