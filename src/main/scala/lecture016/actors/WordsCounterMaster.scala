package lecture016.actors

import akka.actor.{Actor, ActorRef, Props}
import lecture016.commands.{CountWords, Initialize}
import lecture016.events.WordsCounted

class WordsCounterMaster extends Actor {

	override def receive: Receive = {

		case Initialize ( nWorkers: Int ) => {

			val workers = for (

				i <- 0 to ( nWorkers - 1 )
			)
			yield context.actorOf ( Props [ WordsCounterWorker ], s"WordCounterWorker-$i" )

			println ( s"Initialized with ${workers.size} workers" )

			context.become ( initialized ( workers, 0, 0 ) )
		}

		case CountWords ( text: String ) => {

			// TODO Tells the sender I'm not initialized Yet
			println ( "Unable to handle CountWords command since I need Initialize command first" )
		}
	}

	def initialized ( workers: Seq [ ActorRef ], currentWorker: Int, wordCounter: Int ): Receive = {

		// TODO Tells the sender I'm already initialized
		case Initialize => {

			println ( "Already initialized" )
		}

		case CountWords ( text: String ) => {

			println ( s"Sending task to worker $currentWorker" )
			workers ( currentWorker ) ! CountWords ( text )
			context.become ( initialized ( workers, ( currentWorker + 1 ) % workers.length, wordCounter ) )
		}

		case WordsCounted ( count: Int ) => {

			val actualCount = wordCounter + count
			println ( s"Received count from ${sender ().path }, actual count is $actualCount" )
			context.become ( initialized ( workers, currentWorker, actualCount ) )
		}
	}
}