package lecture020.events

import akka.actor.ActorRef

case class WorkerAdded ( worker: ActorRef )