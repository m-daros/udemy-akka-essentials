package lecture020

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import lecture020.actors.{MainActor, WordsCounterMaster, WordsCounterWorker}
import lecture020.commands.{AddWorker, CountWords, Initialize}

object MyApp extends App {

    val actorSystem = ActorSystem ( "lecture020" )

    val mainActor = actorSystem.actorOf ( Props [ MainActor ], "mainActor" )

    mainActor ! Initialize ( actorSystem )

    actorSystem.terminate ()
}