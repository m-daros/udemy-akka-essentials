package lecture010.bankaccount

import akka.actor.{ActorRef, ActorSystem, Props}
import lecture010.bankaccount.actors.{BankAccountActor, PersonActor}
import lecture010.bankaccount.commands.DoWhatYouWant

object MyApp extends App {

	val actorSystem = ActorSystem ( "BankAccApp" )

	val bankAccountActor: ActorRef = actorSystem.actorOf ( Props [ BankAccountActor ], "bankAccountActor" )
	val personActor: ActorRef = actorSystem.actorOf ( Props [ PersonActor ], "personActor" )

	personActor ! DoWhatYouWant ( bankAccountActor )

	actorSystem.terminate ()
}