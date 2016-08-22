name := "ScalaProtocolBuffer"

version := "1.0"

scalaVersion := "2.11.8"

import com.trueaccord.scalapb.{ScalaPbPlugin => PB}
PB.protobufSettings

PB.runProtoc in PB.protobufConfig := (args =>
  com.github.os72.protocjar.Protoc.runProtoc("-v261" +: args.toArray))

// intellij fix
scalaSource in PB.protobufConfig := sourceManaged.value

val circeVersion = "0.4.1"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

//Akka Http
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" % "2.4.9",
  "com.typesafe.akka" %% "akka-http-testkit" % "2.4.9"
)

//Akka Extension for circe json support
resolvers += Resolver.bintrayRepo("hseeberger", "maven")
libraryDependencies ++= List(
  "de.heikoseeberger" %% "akka-http-circe" % "1.9.0"
)