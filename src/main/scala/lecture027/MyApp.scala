package lecture027

import akka.actor.{ActorSystem, Props}
import akka.pattern.{Backoff, BackoffOpts, BackoffSupervisor}
import lecture027.actors.FileReader
import java.time.Duration

import scala.concurrent.duration.DurationInt

object MyApp extends App {

  val actorSystem: ActorSystem = ActorSystem ( "lecture027" )

//  val fileReaderActor = actorSystem.actorOf ( Props [ FileReader ], "fileReaderActor" )

  val backoffProiperties = BackoffSupervisor.props (

    BackoffOpts.onStop (

      Props [ FileReader ],
      childName = "fileReaderActor",
      minBackoff = 3 seconds,
      maxBackoff = 30 seconds,
      randomFactor = 0.2 // adds 20% "noise" to vary the intervals slightly
    ) )

  actorSystem.actorOf ( backoffProiperties, name = "supervisor")

//  actorSystem.terminate ()
}