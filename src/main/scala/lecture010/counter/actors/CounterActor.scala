package lecture010.counter.actors

import akka.actor.Actor
import lecture010.counter.commands.{Decrement, Increment, Print}

class CounterActor extends Actor {

	private var count: Double = 0


	override def receive: Receive = {

		case Increment => {

			count = count + 1;
			println ( s"$self Incremented count to $count" )
		}

		case Decrement => {

			count = count - 1;
			println ( s"$self Decremented count to $count" )
		}

		case Print => {

			println ( s"$self The count is: $count" )
		}
	}
}