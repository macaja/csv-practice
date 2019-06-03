package myob.exercise

import java.io._

import myob.exercise.employee.services.EmployeeServices
import myob.exercise.infrastructure.encoder.{CSV, Parser}
import myob.exercise.infrastructure.file.EmployeeDTO

import scala.io.Source

object Main extends App with EmployeeServices {

  def write(employeePaySlip: String) = {
    val file = new File("src/main/resources/employeesPaySlip")
    val fos = new FileOutputStream(file)
    val bw = new BufferedWriter(new OutputStreamWriter(fos))
    println(s"Info to write: ${employeePaySlip}")
    bw.write(employeePaySlip)
    bw.newLine()
  }

  val s: Unit = {
    import myob.exercise.infrastructure.encoder.Parsers._
    Source
      .fromFile("src/main/resources/employeesGrossIncome.csv")
      .getLines()
      .foreach(
        dto =>
          Parser
            .parse[EmployeeDTO](dto)
            .fold(
              re => println(s"Parsing error: ${re.msg}"),
              dto =>
                calculatePaySlip(dto).fold(
                  de => println(s"Domain Error: ${de.message}"), {
                    import myob.exercise.infrastructure.encoder.CSVs._
                    import cats.instances.all._
                    employee => write(CSV.to(employee))
                  }
                )
            )
      )
  }

}
