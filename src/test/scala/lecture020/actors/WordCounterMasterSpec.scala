package lecture020.actors

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import lecture020.commands.{AddWorker, CountWords}
import lecture020.events.{WordsCounted, WorkerAdded}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.scalatest.wordspec.AnyWordSpecLike

class WordCounterMasterSpec extends TestKit ( ActorSystem ( "WordCounterMasterSpec" ) )
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  "A WortdsCounterMaster should" should {

    "add a new worker" in {

      // Initialize mocks behavior
      val workerMock = TestProbe ( "WordsCounterWorker" )
      val wordsCounterMaster = system.actorOf ( Props [ WordsCounterMaster ], "WordsCounterMaster1" )

      // Test the actor
      wordsCounterMaster ! AddWorker ( workerMock.ref )

      val replyMessage = expectMsgType [ WorkerAdded ]

      assert ( workerMock.ref == replyMessage.worker )
    }

    "assign CountWords job to a worker" in {

      // Initialize mocks behavior
      val workerMock = TestProbe ( "WordsCounterWorker" )

      val wordsCounterMaster = system.actorOf ( Props [ WordsCounterMaster ], "WordsCounterMaster2" )

      // Add a worker
      wordsCounterMaster ! AddWorker ( workerMock.ref )

      // Expect the msg in order to consume it, otherwise other tests can be affected
      expectMsg ( WorkerAdded ( workerMock.ref ) )

      // Test the actor
      wordsCounterMaster ! CountWords ( "The cat is on the table" )

      val expectedCommand = workerMock.expectMsgType [ CountWords ]

      assert ( "The cat is on the table" == expectedCommand.phrase )
    }

    "hold the total words counted" in {

      // Initialize mocks behavior
      val workerMock = TestProbe ( "WordsCounterWorker" )

      val wordsCounterMaster = system.actorOf ( Props [ WordsCounterMaster ], "WordsCounterMaster3" )

      // Add a worker
      wordsCounterMaster ! AddWorker ( workerMock.ref )

      val addWorkerReplyMessage   = expectMsgType [ WorkerAdded ]

      // Test the actor
      wordsCounterMaster ! CountWords ( "The cat is on the table" ) // 6 words

      workerMock.expectMsg ( CountWords ( "The cat is on the table" ) )
      workerMock.reply ( WordsCounted ( 6 ) )

      wordsCounterMaster ! CountWords ( "The grass is green" )      // 4 words

      val countWordsReplyMessage1 = expectMsgType [ WordsCounted ]

      workerMock.expectMsg ( CountWords  ( "The grass is green" ) )
      workerMock.reply ( WordsCounted ( 4 ) )

      val countWordsReplyMessage2 = expectMsgType [ WordsCounted ]

      assert ( countWordsReplyMessage1.wordsCount == 6 )
      assert ( countWordsReplyMessage2.wordsCount == 10 )
    }
  }

  override protected def afterAll: Unit = {

    TestKit.shutdownActorSystem ( system )
  }

}