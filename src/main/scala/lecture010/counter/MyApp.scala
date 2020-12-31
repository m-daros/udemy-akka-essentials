package lecture010.counter

import akka.actor.{ActorRef, ActorSystem, Props}
import lecture010.counter.actors.CounterActor
import lecture010.counter.commands.{Decrement, Increment, Print}

object MyApp extends App {

	val actorSystem = ActorSystem ( "CounterApp" )

	val counterActor: ActorRef = actorSystem.actorOf ( Props [ CounterActor ], "counterActor" )

	counterActor ! Increment
	counterActor ! Increment
	counterActor ! Print

	counterActor ! Decrement
	counterActor ! Decrement
	counterActor ! Print

	actorSystem.terminate ()
}