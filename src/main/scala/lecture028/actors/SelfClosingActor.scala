package lecture028.actors

import akka.actor.{Actor, ActorLogging, Cancellable}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class SelfClosingActor extends Actor with ActorLogging {

  override def postStop (): Unit = {

    log.info ( "Stopped" )
  }

  override def receive: Receive = selfClosing ( createClosingTask () )

  def selfClosing ( closeTask: Cancellable ): Receive = {

    // Cancel the previously scheduled stop and reschedule a new one
    case message => {

      log.info ( s"Received message $message" )
      closeTask.cancel ()
      context.become ( selfClosing ( createClosingTask () ) )
    }
  }

  // Schedule the sop after 1 second
  def createClosingTask () : Cancellable = {

    context.system.scheduler.scheduleOnce ( 1 second ) {

      context.stop ( self )
    }
  }
}