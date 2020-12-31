package lecture016.actors

import akka.actor.Actor
import lecture016.commands.CountWords
import lecture016.events.WordsCounted

class WordsCounterWorker extends Actor {

	override def receive: Receive = {

		case CountWords ( text: String ) => {

			val count = text.split (" ").length
			sender () ! WordsCounted ( count )
		}

		case _ => {

			println ( "Unable to understand the received command" )
		}
	}
}