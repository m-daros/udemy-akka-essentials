package lecture027.actors

import java.io.File

import akka.actor.{Actor, ActorLogging}

import scala.io.Source

class FileReader extends Actor with ActorLogging {


  override def preStart (): Unit = {

    log.info ( "I'm starting" )

    val fileSource: Source = Source.fromFile ( new File ( "src/main/resources/lecture027/test-files/my-file.txt" ) )

    log.info ( s"Readed from file the lines: ${fileSource.getLines ().toList}" )

    super.preStart ()
  }

  override def receive: Receive = {

    case message => {

      log.info ( s"Received message $message" )
    }
  }
}