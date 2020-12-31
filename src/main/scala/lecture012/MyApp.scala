package lecture012

import akka.actor.{ActorSystem, Props}
import lecture010.counter.MyApp.actorSystem
import lecture012.actors.{Kid, Mom}
import lecture012.commands.TakeCareOfYourBaby

object MyApp extends App {

	val actorSystem = ActorSystem ( "ChangingActorBehavior" )

	val momActor = actorSystem.actorOf ( Props [ Mom ], "momActor" )
	val kidActor = actorSystem.actorOf ( Props [ Kid ], "kidActor" )

	momActor ! TakeCareOfYourBaby ( kidActor )

	actorSystem.terminate ()
}