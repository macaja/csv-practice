package myob.exercise.employee.services

import myob.exercise.employee.{DomainError, Employee}
import myob.exercise.infrastructure.file.EmployeeDTO

trait EmployeeServices {

  def calculatePaySlip(dto: EmployeeDTO): DomainError Either Employee =
    for {
      employee <- Employee.validate(dto)
    } yield employee

}
