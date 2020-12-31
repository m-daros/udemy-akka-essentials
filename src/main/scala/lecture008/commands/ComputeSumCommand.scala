package lecture008.commands

import akka.actor.ActorRef

case class ComputeSumCommand ( range: Range, replyTo: ActorRef )