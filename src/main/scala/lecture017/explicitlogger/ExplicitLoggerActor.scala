package lecture017.explicitlogger

import akka.actor.Actor
import akka.event.Logging

class ExplicitLoggerActor extends Actor {

  val logger = Logging ( context.system, this )

  override def receive: Receive = {

    case message => {

      logger.info ( "Received message: {}", message )
    }
  }
}