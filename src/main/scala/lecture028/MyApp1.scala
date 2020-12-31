package lecture028

import akka.actor.{ActorSystem, Props}
import lecture028.actors.SelfClosingActor

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object MyApp1 extends App {

  val actorSystem = ActorSystem ( "lecture028" )

  val selfClosingActor = actorSystem.actorOf ( Props [ SelfClosingActor ], "selfClosingActor" )

  val repeateMessagingTask = actorSystem.scheduler.scheduleAtFixedRate ( 100 milliseconds, 700 milliseconds, selfClosingActor, "my-heartbeat" )

  actorSystem.scheduler.scheduleOnce ( 10 seconds ) {

    repeateMessagingTask.cancel ()
  }

  // actorSystem.terminate ()
}