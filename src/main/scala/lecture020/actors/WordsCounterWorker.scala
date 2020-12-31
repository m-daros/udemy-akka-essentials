package lecture020.actors

import akka.actor.{Actor, ActorLogging}
import lecture020.commands.CountWords
import lecture020.events.WordsCounted

class WordsCounterWorker extends Actor with ActorLogging {

  override def receive: Receive = {

    case CountWords ( phrase: String ) => {

      log.info ( s"Received message CountWords ( ${phrase} )" )
      sender () ! WordsCounted ( phrase.split ( "\\s" ).size )
    }
  }
}