package lecture014.exercise2

import akka.actor.{ActorSystem, Props}
import lecture014.exercise2.actors.{Citizen, VoteAggregator}
import lecture014.exercise2.commands.{AggregateVotes, Vote}

object MyApp extends App {

	val actorSystem = ActorSystem ( "AggregateVotesActorSystem" )

	val alice    = actorSystem.actorOf ( Props [ Citizen ] )
	val bob      = actorSystem.actorOf ( Props [ Citizen ] )
	val charlie  = actorSystem.actorOf ( Props [ Citizen ] )
	val daniel   = actorSystem.actorOf ( Props [ Citizen ] )
	val massimo  = actorSystem.actorOf ( Props [ Citizen ] )

	alice ! Vote ( "Martin" )
	bob ! Vote ( "Jonas" )
	charlie ! Vote ( "Roland" )
	daniel ! Vote ( "Roland" )

	val voteAggregator = actorSystem.actorOf ( Props [ VoteAggregator ] )

	voteAggregator ! AggregateVotes ( Set ( alice, bob, charlie, daniel, massimo ) )

	// Wait before massimo vote
	Thread.sleep ( 5 )

	massimo ! Vote ( "Einstein" )

	voteAggregator ! AggregateVotes ( Set ( alice, bob, charlie, daniel, massimo ) )

	/*
		Print the status of the votes

		Martin -> 1
		Jonas -> 1
		Roland -> 2
	 */

	actorSystem.terminate ()
}