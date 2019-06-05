package myob.exercise.file

import os.Path
import cats.syntax.either._

import scala.util.Try

object FileAction {

  def read(path: Path, file: String): String Either List[String] = {
    Try(os.read.lines(path / file).toList).toEither.leftMap(
      thr => s"Error reading the file $file due to: [${thr.toString}]"
    )
  }

}
