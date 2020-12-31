package lecture014.exercise1.actors

import akka.actor.Actor
import lecture014.exercise1.commands.{Decrement, Increment, Print}

class StatelessCounterActor extends Actor {

	// No mutable state
	// use become to change the behavior pushing receive handlers in a stack
	// Use receive vith parameter
	override def receive: Receive = update ( 0 )

	def update ( actualCount: Double ): Receive = {

		case Increment => {

			val newCount = actualCount + 1
			println ( s"$self Incremented count to $newCount" )
			context.become ( update ( newCount ) )
		}

		case Decrement => {

			val newCount = actualCount - 1
			println ( s"$self Decremented count to $newCount" )
			context.become ( update ( newCount ) )
		}

		case Print => {

			println ( s"$self The count is: $actualCount" )
		}

		case _ => {

			println ( "Unable to understand command" )
		}
	}
}