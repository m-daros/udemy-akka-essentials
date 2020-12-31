package lecture032.actors

import akka.actor.{Actor, ActorLogging, Stash}
import lecture032.commands.{Close, Open, Read, Write}
import lecture032.events.{Closed, DataRetrieved, Opened, Wrote}

class ResourceActor extends Actor with ActorLogging with Stash {

  override def receive: Receive = closed ( null )

  def closed ( myData: String ): Receive = {

    case Open => {

      log.info ( "Opening" )
      unstashAll ()
      context.become ( open ( null ) )
      sender () ! Opened
    }

    case message => {

      log.info ( s"Stashing message $message" )
      stash ()
    }
  }

  def open ( myData: String ): Receive = {

    case Write ( data : String ) => {

      log.info ( s"Writing data $data")
      context.become ( open ( data ) )
      sender () ! Wrote
    }

    case Read => {

      log.info ( s"Retrieved data $myData" )
      sender () ! DataRetrieved ( myData )
    }

    case Close => {

      log.info ( "Closing" )
      context.become ( closed ( myData ) )
      sender () ! Closed
    }
  }
}