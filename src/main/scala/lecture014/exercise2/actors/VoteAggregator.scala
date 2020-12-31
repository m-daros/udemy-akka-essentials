package lecture014.exercise2.actors

import akka.actor.{Actor, ActorRef}
import lecture014.exercise2.commands.{AggregateVotes, VoteStatusReply, VoteStatusRequest}

class VoteAggregator extends Actor {

	// If I receive AggregateVotes for a set of citizens, then I will ask for every on of the using a command VoteStatusRequest
	// And then the citizen will reply with VoteStatusReply with an Optional [ String ]
	override def receive: Receive = updateAggregation ( Set [ ActorRef ] (), Map [ String, Int ] () )

	def updateAggregation ( remainingToVote: Set [ ActorRef ], aggregation: Map [ String, Int ] ): Receive = {

		case AggregateVotes ( citizens: Set [ ActorRef ] ) => {

			context.become ( updateAggregation ( citizens, Map [ String, Int ] () ) )
			citizens.foreach ( citizen => citizen ! VoteStatusRequest )
		}

		case VoteStatusReply ( Some ( candidate ) ) => {

			val votes = aggregation.getOrElse ( candidate, 0 ) + 1
			val updatedAggregation = aggregation + ( candidate -> votes )

			manageVoteStatus ( remainingToVote - sender (), updatedAggregation )
		}

		case VoteStatusReply ( None ) => {

			manageVoteStatus ( remainingToVote - sender (), aggregation )
		}
	}

	private def manageVoteStatus ( remainingToVote: Set [ ActorRef ], aggregation: Map [ String, Int ] ): Unit = {

		if ( remainingToVote.isEmpty ) {

			println ( s"$aggregation" )
		}
		else {

			context.become ( updateAggregation ( remainingToVote, aggregation ) )
		}
	}
}