package myob.exercise.employee.services

import myob.exercise.EmployeeDTO
import myob.exercise.employee.{DomainError, Employee}

object EmployeeServices {

  def calculatePaySlip(dto: EmployeeDTO): DomainError Either Employee =
    Employee.validateAndCalculatePaySlip(dto)

}
