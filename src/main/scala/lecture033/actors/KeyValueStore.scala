package lecture033.actors

import akka.actor.{Actor, ActorLogging}
import lecture033.commands.storage.{Read, Write}

class KeyValueStore extends Actor with ActorLogging {

  override def receive: Receive = manageStorage ( Map () )


  def manageStorage ( storage: Map [ String, String ] ): Receive = {

    case Write ( username: String, password: String ) => {

      context.become ( manageStorage ( storage + ( username -> password ) ) )
    }

    case Read ( username : String ) => {

      sender () ! storage.get ( username )
    }
  }
}