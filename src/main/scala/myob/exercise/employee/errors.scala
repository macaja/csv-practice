package myob.exercise.employee

sealed trait DomainError {
  def message: String
}
case class NegativeLongValue(message: String) extends DomainError

case class InvalidRateValue(message: String) extends DomainError

case class SuperRateOutOfRank(
    message: String = "The value of the super is out of rank"
) extends DomainError
