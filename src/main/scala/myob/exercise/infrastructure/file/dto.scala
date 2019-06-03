package myob.exercise.infrastructure.file

case class EmployeeDTO(
    firstName: String,
    lastName: String,
    annualSalary: Long,
    superRate: String, //TODO: Check type coz' it will come as '9%'
    paymentRankDates: String //TODO: It will be '01 March - 31 March'
)
