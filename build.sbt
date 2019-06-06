import Dependencies._

ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "myob.exercise.employees"
ThisBuild / organizationName := "exercise"

ThisBuild / developers := List(
  Developer(
    id    = "macaja",
    name  = "Mauricio Cardona Jaramillo",
    email = "cardonajaramillomauricio@gmail.com",
    url   = url("https://github.com/macaja")
  )
)

lazy val myobExercise = (project in file("."))
  .settings(
    name := "myob-exercise",
    addCompilerPlugin(scalafixSemanticdb),
    libraryDependencies ++= Seq(
      shapeless,
      cats,
      osLib,
      logBack,
      scalaLogging,
      scalaCheck % Test,
      scalaTest % Test
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "utf-8",
      "-explaintypes",
      "-feature",
      "-language:existentials",
      "-language:experimental.macros",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      "-Xcheckinit",
      "-Xfatal-warnings",
      "-Xfuture",
      "-Xlint:adapted-args",
      "-Xlint:by-name-right-associative",
      "-Xlint:constant",
      "-Xlint:delayedinit-select",
      "-Xlint:doc-detached",
      "-Xlint:inaccessible",
      "-Xlint:infer-any",
      "-Xlint:missing-interpolator",
      "-Xlint:nullary-override",
      "-Xlint:nullary-unit",
      "-Xlint:option-implicit",
      "-Xlint:package-object-classes",
      "-Xlint:poly-implicit-overload",
      "-Xlint:private-shadow",
      "-Xlint:stars-align",
      "-Xlint:type-parameter-shadow",
      "-Xlint:unsound-match",
      "-Yno-adapted-args",
      "-Ypartial-unification",
      "-Ywarn-dead-code",
      "-Ywarn-extra-implicit",
      "-Ywarn-inaccessible",
      "-Ywarn-infer-any",
      "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit",
      "-Ywarn-unused:implicits",
      "-Ywarn-unused:imports",
      "-Ywarn-unused:locals",
      "-Ywarn-unused:params",
      "-Ywarn-unused:patvars",
      "-Ywarn-unused:privates",
      "-Ywarn-value-discard",
      "-Yrangepos"
    ),
    scalacOptions in (Compile, console) ~= (_.filterNot(Set(
      "-Ywarn-unused:imports",
      "-Xfatal-warnings"
    )))
  )

scalafmtOnCompile in ThisBuild := true
