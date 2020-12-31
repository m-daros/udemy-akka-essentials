package lecture033.actors

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSelection}
import lecture033.commands.auth.{Authenticate, Register}
import lecture033.commands.storage.{Read, Write}

import scala.concurrent.{ExecutionContextExecutor, Future}
import akka.pattern.ask
import akka.util.Timeout
import lecture033.events.auth.{Authenticated, AuthenticationDenied, AuthenticationFailed}

import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

class AuthManager extends Actor with ActorLogging {

  val keyValueStore: ActorSelection = context.actorSelection ( "akka://lecture033/user/keyValueStoreActor" )

  override def receive: Receive = {

    case Register ( username: String, password: String ) => {

      keyValueStore ! Write ( username, password )
    }

    case Authenticate ( username: String, password: String ) => {

      implicit val timeout: Timeout = Timeout ( 1 second )
      implicit val executionContext: ExecutionContextExecutor = context.dispatcher // TODO Capire se ha ripercussioni sui thread del dispatcher

      val passwordToCheck = keyValueStore ? Read ( username )

      val originalSender: ActorRef = sender ()

      passwordToCheck.onComplete {

        case Failure ( exception ) => {

          log.error ( s"Unable to authenticate user $username due to system error", exception )
          originalSender ! AuthenticationFailed ( s"Authentication failed for user $username due to system error" )
        }

        case Success ( None ) => {

          log.info ( s"Authentication denied for user $username" )
          originalSender ! AuthenticationDenied ( s"Authentication denied for user $username" )
        }

        case Success ( Some ( savedPassword ) ) => {

          if ( savedPassword == password ) {

            log.info ( s"User $username authenticated" )
            originalSender ! Authenticated
          }
          else {

            log.info ( s"Authentication denied for user $username" )
            AuthenticationDenied ( s"Authentication denied for user $username" )
          }
        }
      }
    }
  }
}