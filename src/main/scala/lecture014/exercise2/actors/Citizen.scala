package lecture014.exercise2.actors

import akka.actor.Actor
import lecture014.exercise2.commands.{Vote, VoteStatusReply, VoteStatusRequest}

class Citizen extends Actor {

	override def receive: Receive = {

		case Vote ( candidate: String ) => {

			context.become ( receiveHavingVotedYet ( candidate ) )
		}

		case VoteStatusRequest => {

			println ( s"$self I not voted yet !!" )
			sender () ! VoteStatusReply ( Option.empty [ String ] )
		}
	}

	def receiveHavingVotedYet ( candidate: String ): Receive = {

		case Vote ( candidate: String ) => {

		}

		case VoteStatusRequest => {

			println ( s"$self I voted for $candidate" )
			sender () ! VoteStatusReply ( Option [ String ] ( candidate ) )
		}
	}
}