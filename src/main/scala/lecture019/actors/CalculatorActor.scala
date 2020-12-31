package lecture019.actors

import akka.actor.Actor
import lecture019.commands.{AddNumbers, DivideNumbers, MultiplyNumbers, SubtractNumbers}
import lecture019.events.{CalculatedAmount, UnknownCommand}

class CalculatorActor extends Actor {

  override def receive: Receive = {

    case AddNumbers ( numbers: Seq [ Double ] ) => {

      sender () ! CalculatedAmount ( numbers.reduceLeft [ Double ] ( _ + _ ) )
    }

    case SubtractNumbers ( numbers: Seq [ Double ] ) => {

      sender () ! CalculatedAmount ( numbers.reduceLeft [ Double ] ( _ - _ ) )
    }

    case MultiplyNumbers ( numbers: Seq [ Double ] ) => {

      sender () ! CalculatedAmount ( numbers.reduceLeft [ Double ] ( _ * _ ) )
    }

    case DivideNumbers ( numbers: Seq [ Double ] ) => {

      sender () ! CalculatedAmount ( numbers.reduceLeft [ Double ] ( _ / _ ) )
    }

    // Handle unknown commands
    case command => {

      sender () ! UnknownCommand ( command )
    }
  }
}