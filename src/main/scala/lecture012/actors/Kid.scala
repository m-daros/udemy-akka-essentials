package lecture012.actors

import akka.actor.Actor
import lecture012.commands.{AskSomething, Eat}
import lecture012.commands.Food._
import lecture012.events.{Accept, Reject}

class Kid extends Actor {

	override def receive: Receive = receiveWhenImHappy

	def receiveWhenImHappy: Receive = {

		case Eat ( CHOCOLATE ) => {

			println ( s"Yeah!! I love $CHOCOLATE" )
		}

		case Eat ( VEGETABLES ) => {

			context.become ( receiveWhenImSad )
		}

		case AskSomething ( what ) => {

			println ( s"OK, I'm happy to $what" )
			sender () ! Accept
		}
	}

	def receiveWhenImSad: Receive = {

		case Eat ( VEGETABLES ) => {

			println ( s"Bleah!! another damn $VEGETABLES" )
		}

		case Eat ( CHOCOLATE ) => {

			println ( s"I like $CHOCOLATE, I'm happy !!!" )
			context.become ( receiveWhenImHappy )
		}

		case AskSomething ( what ) => {

			println ( s"NO !!, I don't whant to $what" )
			sender () ! Reject
		}
	}
}