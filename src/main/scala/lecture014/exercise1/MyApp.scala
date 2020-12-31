package lecture014.exercise1

import akka.actor.{ActorRef, ActorSystem, Props}
import lecture014.exercise1.actors.StatelessCounterActor
import lecture014.exercise1.commands.{Decrement, Increment, Print}

object MyApp extends App {

	val actorSystem = ActorSystem ( "CounterApp" )

	val counterActor: ActorRef = actorSystem.actorOf ( Props [ StatelessCounterActor ], "statelessCounterActor" )

	counterActor ! Increment
	counterActor ! Increment
	counterActor ! Print

	counterActor ! Decrement
	counterActor ! Decrement
	counterActor ! Print

	actorSystem.terminate ()
}