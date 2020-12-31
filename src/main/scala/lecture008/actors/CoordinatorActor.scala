package lecture008.actors

import akka.actor.{Actor, ActorRef, Props}
import lecture008.AppContext
import lecture008.commands.ComputeSumCommand
import lecture008.events.SumComputedEvent

case class CoordinatorActor ( workers: Seq [ ActorRef ] ) extends Actor {

//	private val workers = 1 to 10
//	private var sum: Int = 0

	override def receive: Receive = {

		case command: ComputeSumCommand => {

//			val computed = for {
//
//				worker <- workers
//				i <- command.range if i % worker == 0
//			}
//			yield i

		}

		case _ => println ( s"Unable to understand message" )
	}
}

//object CoordinatorActor {
//
//	def props ( numWorkers: Int ): Props [ CoordinatorActor ] = {
//
//		1 to 10 flatMap ( i => AppContext.getActorSystem ().actorOf ( Props [ AdderActor ], "adder" + i ) )
//	}
//}