package lecture016

import akka.actor.{ActorSystem, Props}
import lecture016.actors.WordsCounterMaster
import lecture016.commands.{CountWords, Initialize}

object MyApp extends App {

	// Distributed word counting
	val actorSystem = ActorSystem ( "DistributedWordCounting" )

	val wordCounterMaster = actorSystem.actorOf ( Props [ WordsCounterMaster ], "WordCounterMaster" )

	// Initialize with 10 workers
	wordCounterMaster ! Initialize ( 10 )

	wordCounterMaster ! CountWords ( "You walk through the subway, his eyes burn a hole in your back" )
	wordCounterMaster ! CountWords ( "The devil send the beast with wrath" )
	wordCounterMaster ! CountWords ( "I will return" )
	wordCounterMaster ! CountWords ( "Today is born the seventh one" )
	wordCounterMaster ! CountWords ( "Seventh son of a seventh son" )
	wordCounterMaster ! CountWords ( "Killer behind you" )
	wordCounterMaster ! CountWords ( "You are number six" )
	wordCounterMaster ! CountWords ( "I am not a number" )
	wordCounterMaster ! CountWords ( "I am a free man" )
	wordCounterMaster ! CountWords ( "It is a number six hundred and sixty six" )
	wordCounterMaster ! CountWords ( "The number of the beast" )

	actorSystem.terminate ()
}