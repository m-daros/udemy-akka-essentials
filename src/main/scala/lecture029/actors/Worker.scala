package lecture029.actors

import akka.actor.{Actor, ActorLogging}
import lecture029.commands.{Compute, Info}
import lecture029.events.Computed

import scala.util.Random

class Worker extends Actor with ActorLogging {

  override def receive: Receive = calculate ( new Random, countIn = 0, countTotal = 0 )

  def calculate ( randomGenerator: Random, countIn: Long, countTotal: Long ): Receive = {

    case Compute ( numIterations: Int ) => {

      implicit val random: Random = randomGenerator
      val insideCircleSum = ( 1 to numIterations ).map ( ( i ) => compute () ).foldLeft ( 0 ) { case ( accA, a ) => ( accA + a ) }

      sender () ! Computed ( insideCircleSum, numIterations )
    }

    case Info => {

      sender () ! Computed ( countIn, countTotal )
    }
  }

  private def compute () ( implicit random: Random ): Int = {

    val x: Double = random.nextDouble ()
    val y: Double = random.nextDouble ()

    // Check if the point is in or out of the circle
    if ( x * x + y * y > 1 ) {

      0
    }
    else {

      1
    }
  }
}