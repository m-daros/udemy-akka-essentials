package lecture032.actors

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import lecture032.commands.{Close, Open, Read, Write}
import lecture032.events.{Closed, DataRetrieved, Opened, Wrote}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class ResourceActorSpec extends TestKit ( ActorSystem ( "ResourceActorSpec" ) )
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  "A ResourceActor should" should {

    "stash messages if it is not open" in {

      val resourceActor = system.actorOf ( Props [ ResourceActor ], "resourceActor" )

      // Test the actor
      resourceActor ! Write ( data = "my-data-1" )
      resourceActor ! Read
      resourceActor ! Open

      expectMsg ( Opened )
      expectMsg ( Wrote )
      expectMsg ( DataRetrieved ( "my-data-1" ) )

      resourceActor ! Read

      expectMsg ( DataRetrieved ( "my-data-1" ) )

      resourceActor ! Close

      expectMsg ( Closed )
    }
  }

  override protected def afterAll: Unit = {

    TestKit.shutdownActorSystem ( system )
  }
}