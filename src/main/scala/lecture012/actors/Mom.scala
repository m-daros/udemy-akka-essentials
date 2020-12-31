package lecture012.actors

import akka.actor.{Actor, ActorRef}
import lecture012.commands.{AskSomething, Eat, TakeCareOfYourBaby}
import lecture012.commands.Food._
import lecture012.events.{Accept, Reject}

class Mom extends Actor {

	override def receive: Receive = {

		case TakeCareOfYourBaby ( kid: ActorRef ) => {

			kid ! AskSomething ( "play some funny game" )
			kid ! Eat ( CHOCOLATE )
			kid ! AskSomething ( "read a book" )
			kid ! Eat ( VEGETABLES )
			kid ! AskSomething ( "whatch the TV" )
		}

		case Accept => {

			println ( "My baby is Happy :) !!!" )
		}

		case Reject => {

			println ( "My baby is sad :(" )
		}

		case message => {

			println ( s"Unable to understand the message: $message" )
		}
	}
}