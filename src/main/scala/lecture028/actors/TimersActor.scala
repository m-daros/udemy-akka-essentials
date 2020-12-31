package lecture028.actors

import akka.actor.{Actor, ActorLogging, Timers}
import lecture028.commands.{Heartbeat, Start, Stop}
import lecture028.model.HeartbeatKey

import scala.concurrent.duration.DurationInt

class TimersActor extends Actor
  with ActorLogging
  with Timers {

  override def receive: Receive = {

    case Start => {

      log.info ( "Starting heartbeat" )
      timers.startTimerAtFixedRate ( HeartbeatKey, Heartbeat, 1 second )
    }

    case Stop => {

      log.info ( "Stopping heartbeat" )
      timers.cancel ( HeartbeatKey )
    }

    case Heartbeat => {

      log.info ( "I'm alive" )
    }
  }


}