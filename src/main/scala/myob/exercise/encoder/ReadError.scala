package myob.exercise.encoder

trait ReadError {
  def msg: String
}

final case class EmptyError(msg: String = "Field Empty") extends ReadError
final case class IntError(msg: String = "Int error") extends ReadError
