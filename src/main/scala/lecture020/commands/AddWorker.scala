package lecture020.commands

import akka.actor.ActorRef

case class AddWorker ( worker: ActorRef )