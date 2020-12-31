package lecture008.actors

import akka.actor.Actor
import lecture008.commands.ComputeSumCommand
import lecture008.events.SumComputedEvent

class AdderActor extends Actor {

	private var sum: Int = 0

	override def receive: Receive = {

		case command: ComputeSumCommand => {

			command.range.foreach ( n => sum = sum + n )
			command.replyTo ! SumComputedEvent ( sum )
		}

		case _ => println ( s"Unable to understand message" )
	}
}