package lecture014.exercise2.commands

import akka.actor.ActorRef

case class AggregateVotes ( citizens: Set [ ActorRef ] )