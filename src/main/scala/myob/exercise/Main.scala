package myob.exercise

import java.io._

import myob.exercise.employee.services.EmployeeServices
import myob.exercise.infrastructure.encoder.{CSV, Parser}
import myob.exercise.infrastructure.file.EmployeeDTO

import scala.io.Source

object Main extends App with EmployeeServices {
  import myob.exercise.infrastructure.encoder.Parsers._

  val file = new File("src/main/resources/employeesPaySlip.csv")
  val bw = new BufferedWriter(new FileWriter(file))

  def processRecords(): Unit = {
    Source
      .fromFile("src/main/resources/employeesGrossIncome.csv")
      .getLines()
      .foreach(
        record =>
          Parser
            .parse[EmployeeDTO](record)
            .fold(
              re => println(s"Parsing error: ${re.msg}"),
              dto =>
                calculatePaySlip(dto).fold(
                  de => println(s"Domain Error: ${de.message}"), {
                    import myob.exercise.infrastructure.encoder.CSVs._
                    import cats.instances.all._
                    employee => bw.write(s"${CSV.to(employee)}\n")
                  }
                )
            )
      )
  }
  processRecords()
  bw.close()
}
