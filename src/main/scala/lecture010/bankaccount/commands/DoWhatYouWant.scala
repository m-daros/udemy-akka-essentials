package lecture010.bankaccount.commands

import akka.actor.ActorRef

case class DoWhatYouWant ( bankAccount: ActorRef )
