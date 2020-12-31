package lecture028

import akka.actor.{ActorSystem, Props}
import lecture028.MyApp1.{actorSystem, repeateMessagingTask}
import lecture028.actors.{SelfClosingActor, TimersActor}
import lecture028.commands.{Start, Stop}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object MyApp2 extends App {

  val actorSystem = ActorSystem ( "lecture028" )

  val timersActor = actorSystem.actorOf ( Props [ TimersActor ], "timersActor" )

  timersActor ! Start

  actorSystem.scheduler.scheduleOnce ( 10 seconds ) {

    timersActor ! Stop
  }

//  actorSystem.terminate ()
}