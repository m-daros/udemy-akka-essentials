package lecture017.actorlogging

import akka.actor.{Actor, ActorLogging}

class ActorLoggingActor extends Actor with ActorLogging {

  override def receive: Receive = {

    case message => {

      log.info ( "Received message: {}", message )
    }
  }
}