package myob.exercise

import com.typesafe.scalalogging.LazyLogging
import myob.exercise.employee.services.EmployeeServices
import myob.exercise.encoder.{CSV, Parser}
import myob.exercise.file.FileAction

trait FileProcessManagement extends LazyLogging {

  def processGrossEmployees(wd: os.Path, fileName: String): Unit = {
    import myob.exercise.encoder.Parsers._
    FileAction
      .read(wd, fileName)
      .fold[Unit](
        err => logger.error(s"$err"),
        _.foreach { record =>
          Parser
            .parse[EmployeeDTO](record)
            .fold(
              re =>
                logger.error(s"Error parsing record: ${record} [[${re.msg}]]"),
              dto =>
                EmployeeServices
                  .calculatePaySlip(dto)
                  .fold(
                    de => logger.error(s"Domain Error: ${de.message}"), {
                      import myob.exercise.encoder.CSVs._
                      import cats.instances.all._
                      employee =>
                        os.write.append(
                          wd / "employeesPaySlip.csv",
                          s"${CSV.to(employee)}\n"
                        )
                    }
                  )
            )
        }
      )
  }

}
