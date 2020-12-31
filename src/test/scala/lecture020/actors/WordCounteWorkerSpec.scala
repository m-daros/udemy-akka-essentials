package lecture020.actors

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import lecture020.commands.{AddWorker, CountWords}
import lecture020.events.{WordsCounted, WorkerAdded}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class WordCounteWorkerSpec extends TestKit ( ActorSystem ( "WordCounterWorkerSpec" ) )
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  "A WortdsCounterWorker should" should {

    "count the words in a phrase" in {

      val wordsCounterWorker = system.actorOf ( Props [ WordsCounterWorker ], "WordsCounterWorker1" )

      // Test the actor
      wordsCounterWorker ! CountWords ( phrase = "Nel` mezzo del cammin di nostra vita" )

      val replyMessage1 = expectMsgType [ WordsCounted ]

      wordsCounterWorker ! CountWords ( phrase = "mi ritrovai per una selva oscura" )

      val replyMessage2 = expectMsgType [ WordsCounted ]

      assert ( replyMessage1.wordsCount == 7 )
      assert ( replyMessage2.wordsCount == 6 )
    }

  }

  override protected def afterAll: Unit = {

    TestKit.shutdownActorSystem ( system )
  }
}