package lecture020.actors

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import lecture020.MyApp.actorSystem
import lecture020.commands.{AddWorker, CountWords, Initialize}

class MainActor extends Actor with ActorLogging {

  override def receive: Receive = {

    case Initialize ( actorSystem: ActorSystem ) => {

      val counterWorker1 = actorSystem.actorOf ( Props [ WordsCounterWorker ], "wordsCounterWorker1" )
      val counterWorker2 = actorSystem.actorOf ( Props [ WordsCounterWorker ], "wordsCounterWorker2" )
      val counterWorker3 = actorSystem.actorOf ( Props [ WordsCounterWorker ], "wordsCounterWorker3" )

      val counterMaster = actorSystem.actorOf ( Props [ WordsCounterMaster ], "wordsCounterMaster" )

      counterMaster ! AddWorker ( counterWorker1 )
      counterMaster ! AddWorker ( counterWorker2 )
      counterMaster ! AddWorker ( counterWorker3 )

      counterMaster ! CountWords ( "Nel mezzo del cammin di nostra vita" )
      counterMaster ! CountWords ( "mi ritrovai per una selva oscura" )
    }

    case message => {

      log.info ( s"Receive d message $message" )
    }
  }
}