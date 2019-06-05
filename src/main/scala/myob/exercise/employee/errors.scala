package myob.exercise.employee

sealed trait DomainError {
  def message: String
}

final case class EmptyString(message: String) extends DomainError
final case class NegativeLongValue(message: String) extends DomainError
final case class InvalidRateValue(message: String) extends DomainError
final case class SuperRateOutOfRank(
    message: String = "The value of the super is out of rank"
) extends DomainError
