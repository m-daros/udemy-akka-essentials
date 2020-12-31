package lecture010.bankaccount.actors

import akka.actor.Actor
import lecture010.bankaccount.commands.{DepositAmount, Statement, WithdrawAmount}
import lecture010.bankaccount.events.{Failure, Success}

class BankAccountActor extends Actor {

	private var balance: Double = 0;


	override def receive: Receive = {

		case DepositAmount ( amount ) => {

			if ( amount < 0 ) {

				sender () ! Failure ( s"$self Illegal negative amount $amount" )
			}
			else {

				balance = balance + amount
				sender () ! Success ( s"$self Successfully deposited $amount, actual balance is: $balance" )
			}
		}

		case WithdrawAmount ( amount ) => {

			if ( amount < 0 ) {

				sender () ! Failure ( s"$self Illegal negative amount $amount" )
			}
			else if ( balance < amount ) {

				sender () ! Failure ( s"$self Unable to withdraw amount $amount: Insufficient balance $balance" )
			}
			else {

				balance = balance - amount
				sender () ! Success ( s"$self Successfully withdrawn $amount, actual balance is: $balance" )
			}
		}

		case Statement => {

			println ( s"$self Actual balance is: $balance" )
		}
	}
}