package lecture033

import akka.actor.{ActorRef, ActorSystem, Props}
import lecture033.actors.{AuthManager, KeyValueStore}
import lecture033.commands.auth.{Authenticate, Register}

object MyApp extends App {

  val actorSystem = ActorSystem ( "lecture033" )

  val authManagerActor: ActorRef = actorSystem.actorOf ( Props [ AuthManager ], "authManagerActor" )
  val keyValueStoreActor: ActorRef = actorSystem.actorOf ( Props [ KeyValueStore ], "keyValueStoreActor" )

  authManagerActor ! Authenticate ( "jack", "jack-pwd" )

  authManagerActor ! Register ( "bob", "bob-pwd" )
  authManagerActor ! Authenticate ( "bob", "wrong-bob-pwd" )
  authManagerActor ! Authenticate ( "bob", "bob-pwd" )

  actorSystem.terminate ()
}