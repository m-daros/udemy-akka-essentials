name := "udemy-akka-essentials"

version := "0.1"

scalaVersion := "2.13.2"

val akkaVersion = "2.6.6"

libraryDependencies ++= Seq (

	"com.typesafe.akka" %% "akka-actor" % akkaVersion,
	"com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
	"org.scalatest" %% "scalatest" % "3.1.2" % Test
)