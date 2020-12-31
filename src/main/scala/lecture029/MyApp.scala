package lecture029

import akka.actor.{ActorSystem, Props}
import akka.routing.FromConfig
import com.typesafe.config.{Config, ConfigFactory}
import lecture029.actors.{Master, Worker}
import lecture029.commands.{CalculatePi, Initialize}

object MyApp extends App {

  val actorSystem = ActorSystem ( "lecture029", ConfigFactory.load ().getConfig ( "lecture029" ) )

  val masterActor = actorSystem.actorOf ( Props [ Master ], "master" )
  val routerActor = actorSystem.actorOf ( FromConfig.props ( Props [ Worker ] ), "poolRouter" )

  masterActor ! Initialize ( routerActor )

  masterActor ! CalculatePi ( 1000000000000L )
//  masterActor ! CalculatePi ( 10000000 )
}