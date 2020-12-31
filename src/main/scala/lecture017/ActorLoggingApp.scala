package lecture017

import akka.actor.{ActorSystem, Props}
import lecture017.actorlogging.ActorLoggingActor
import lecture017.explicitlogger.ExplicitLoggerActor

object ActorLoggingApp extends App {

  val actorSystem = ActorSystem ( "ActorLoggingApp" )

  val explicitLoggerActor = actorSystem.actorOf ( Props [ ExplicitLoggerActor ], "explicitLoggerActor" )
  val actorLoggingActor   = actorSystem.actorOf ( Props [ ActorLoggingActor ], "actorLoggingActor" )

  explicitLoggerActor ! "Logging with explicit logger ..."
  actorLoggingActor ! "Logging with ActorLogging trait ..."

  actorSystem.terminate ()
}