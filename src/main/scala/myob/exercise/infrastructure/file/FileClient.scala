package myob.exercise.infrastructure.file

import scala.io.{BufferedSource, Source}
import cats.syntax.either._
import scala.util.Try

object FileClient {

  def download(fileLocation: String): String Either BufferedSource =
    Try(Source.fromFile(fileLocation)).fold(
      _ => "No file founded to be downloaded".asLeft,
      _.asRight
    )

}
