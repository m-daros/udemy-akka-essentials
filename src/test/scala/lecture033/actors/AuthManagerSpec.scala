package lecture033.actors

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import lecture033.commands.auth.Authenticate
import lecture033.commands.storage.Read
import org.scalatest.BeforeAndAfterAll
import org.scalatest.wordspec.AnyWordSpecLike

class AuthManagerSpec extends TestKit ( ActorSystem ( "AuthManagerSpec" ) )
  with ImplicitSender
  with AnyWordSpecLike
  with BeforeAndAfterAll {

  "An AuthManager should" should {

    "Grant authentication when user provides correct username and password" in {

      // Initialize mocks behavior
      val keyValueStoreActorMock = TestProbe ( "KeyValueStore" )

      val authManagerActor = system.actorOf ( Props [ AuthManager ] )

      authManagerActor ! Authenticate ( "my-username", "my-password" )

      keyValueStoreActorMock.expectMsg ( Read ( "my-username" ) )
    }
  }

  override protected def afterAll: Unit = {

    TestKit.shutdownActorSystem ( system )
  }
}