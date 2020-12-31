package lecture020.actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import lecture020.commands.{AddWorker, CountWords}
import lecture020.events.{WordsCounted, WorkerAdded}

class WordsCounterMaster extends Actor with ActorLogging {

  override def receive: Receive = {

    case AddWorker ( worker ) => {

      log.info ( "Received message: {}", AddWorker ( worker ) )

      val originalSender = sender ()

      context.become ( holdingWorkers ( Seq ( worker ), actualCount = 0, actualWorker = 0, originalSender ) )
      originalSender ! WorkerAdded ( worker )
    }
  }

  def holdingWorkers ( workers: Seq [ ActorRef ], actualCount: Long, actualWorker: Int, originalSender: ActorRef ) : Receive = {

    case CountWords ( phrase: String ) => {

      log.info ( "Received message: {}", CountWords ( phrase ) )

      workers ( actualWorker ) ! CountWords ( phrase )
      context.become ( holdingWorkers ( workers, actualCount, getNextWorker ( workers.size, actualWorker ), originalSender ) )
    }

    case WordsCounted ( wordsCount ) => {

      log.info ( "Received message: {}", WordsCounted ( wordsCount ) )

      val totalCount = actualCount + wordsCount

      originalSender ! WordsCounted ( totalCount )
      context.become ( holdingWorkers ( workers, actualCount = totalCount, actualWorker, originalSender ) )
    }

    case AddWorker ( worker: ActorRef ) => {

      log.info ( "Received message: {}", AddWorker ( worker ) )

      sender () ! WorkerAdded ( worker )
      context.become ( holdingWorkers ( workers :+ worker, 0, actualWorker, originalSender ) )
    }

  }

  // TODO TESTARE
  def getNextWorker ( numWorkers: Int, actualWorker: Int ): Int = {

    ( actualWorker + 1 ) % numWorkers
  }
}