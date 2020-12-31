package lecture008

import akka.actor.ActorSystem

object AppContext {

	def getActorSystem (): ActorSystem = {

		ActorSystem ( "DistributedSumActorSystem" )
	}
}