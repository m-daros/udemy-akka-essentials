package lecture019.actors

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import lecture019.commands.{AddNumbers, DivideNumbers, MultiplyNumbers, SubtractNumbers}
import lecture019.events.{CalculatedAmount, UnknownCommand}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class CalculatorActorSpec extends TestKit ( ActorSystem ( "CalculatorActorSpec" ) )
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  "A CalculatorActor should" should {

    "add 2 numbers" in {

      val calculatorActor = system.actorOf ( Props [ CalculatorActor ] )

      // Send message to actor to test
      calculatorActor ! AddNumbers ( Seq ( 1, 2 ) )

      // Receive response message
      val response = expectMsgType [ CalculatedAmount ]

      // Assertions
      assert ( response.number == 3 )
    }

    "subtract 2 numbers" in {

      val calculatorActor = system.actorOf ( Props [ CalculatorActor ] )

      // Send message to actor to test
      calculatorActor ! SubtractNumbers ( Seq ( 7, 2 ) )

      // Receive response message
      val response = expectMsgType [ CalculatedAmount ]

      // Assertions
      assert ( response.number == 5 )
    }

    "multiply 2 numbers" in {

      val calculatorActor = system.actorOf ( Props [ CalculatorActor ] )

      // Send message to actor to test
      calculatorActor ! MultiplyNumbers ( Seq ( 7, 2 ) )

      // Receive response message
      val response = expectMsgType [ CalculatedAmount ]

      // Assertions
      assert ( response.number == 14 )
    }

    "divide 2 numbers" in {

      val calculatorActor = system.actorOf ( Props [ CalculatorActor ] )

      // Send message to actor to test
      calculatorActor ! DivideNumbers ( Seq ( 7, 2 ) )

      // Receive response message
      val response = expectMsgType [ CalculatedAmount ]

      // Assertions
      assert ( response.number == 3.5 )
    }

    "add a sequence of numbers" in {

      val calculatorActor = system.actorOf ( Props [ CalculatorActor ] )

      // Send message to actor to test
      calculatorActor ! AddNumbers ( Seq ( 1, 2, 3, 4 ) )

      // Receive response message
      val response = expectMsgType [ CalculatedAmount ]

      // Assertions
      assert ( response.number == 10 )
    }

    "subtract a sequence of numbers" in {

      val calculatorActor = system.actorOf ( Props [ CalculatorActor ] )

      // Send message to actor to test
      calculatorActor ! SubtractNumbers ( Seq ( 7, 2, 1, 0 ) )

      // Receive response message
      val response = expectMsgType [ CalculatedAmount ]

      // Assertions
      assert ( response.number == 4 )
    }

    "multiply a sequence of numbers" in {

      val calculatorActor = system.actorOf ( Props [ CalculatorActor ] )

      // Send message to actor to test
      calculatorActor ! MultiplyNumbers ( Seq ( 7, 2, 3 ) )

      // Receive response message
      val response = expectMsgType [ CalculatedAmount ]

      // Assertions
      assert ( response.number == 42 )
    }

    "divide a sequence of numbers" in {

      val calculatorActor = system.actorOf ( Props [ CalculatorActor ] )

      // Send message to actor to test
      calculatorActor ! DivideNumbers ( Seq ( 18, 2, 3 ) )

      // Receive response message
      val response = expectMsgType [ CalculatedAmount ]

      // Assertions
      assert ( response.number == 3 )
    }

    "reply with event UnknownCommand when receiving a command tha is n ot able to recognize" in {

      val calculatorActor = system.actorOf ( Props [ CalculatorActor ] )

      // Send message to actor to test
      calculatorActor ! "This is an unknown command for you"

      // Receive response message
      val response = expectMsgType [ UnknownCommand ]

      // Assertions
      assert ( response.command == "This is an unknown command for you"  )
    }
  }

  override protected def afterAll (): Unit = {

    TestKit.shutdownActorSystem ( system )
  }
}