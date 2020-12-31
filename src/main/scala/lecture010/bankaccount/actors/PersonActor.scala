package lecture010.bankaccount.actors

import akka.actor.Actor
import lecture010.bankaccount.commands.{DepositAmount, DoWhatYouWant, Statement, WithdrawAmount}

class PersonActor extends Actor {

	override def receive: Receive = {

		case DoWhatYouWant ( accountActor ) => {

			accountActor ! DepositAmount ( 10000 )
			accountActor ! WithdrawAmount ( 20000 )
			accountActor ! WithdrawAmount ( 500 )
			accountActor ! Statement
		}

		case message => {

			println ( message.toString )
		}
	}
}