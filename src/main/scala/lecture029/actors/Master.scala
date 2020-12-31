package lecture029.actors

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.routing.Broadcast
import lecture029.commands.{CalculatePi, Compute, Info, Initialize}
import lecture029.events.Computed

class Master extends Actor with ActorLogging {

  val workerIterations = 10000000

  override def receive: Receive = {

    case Initialize ( router: ActorRef ) => {

      context.become ( initialized ( router, numIterations = 0, countIn = 0, countTotal = 0 ) )
      log.info ( "Initialized" )
    }
  }

  def initialized ( router: ActorRef, numIterations: Long, countIn: Long, countTotal: Long ): Receive = {

    case CalculatePi ( numIterations: Long ) => {

      log.info ( s"Approximating PI with $numIterations iterations" )

      context.become ( initialized ( router, numIterations, countIn = 0, countTotal = 0 ) )

      for ( i <- 1 to ( numIterations / workerIterations ).toInt ) {

        router ! Compute ( workerIterations )
      }

      router ! Broadcast ( Info )
    }

    case Computed ( numIn: Long, numTotal : Long ) => {

      log.info ( s"Iterations: ${ countTotal + numTotal } ( ${ 100 * ( countTotal + numTotal ) / numIterations.toDouble }% ) - approximation of PI: ${ 4 * ( countIn + numIn ) / ( countTotal + numTotal ).toDouble }" )
      context.become ( initialized ( router, numIterations, countIn + numIn, countTotal + numTotal ) )
    }
  }
}