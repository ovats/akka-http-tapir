package com.gm

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.gm.routes.{GetOneQueryParameter, GetOneQueryParameterAndError, PostSimple}

import scala.util.{Failure, Success}

object TapirSample {

  def main(args: Array[String]): Unit = {
    println("Starting TapirSample ...")

    implicit val system: ActorSystem = ActorSystem()
    import system.dispatcher

    val routes =
      GetOneQueryParameter.Akka.route ~ GetOneQueryParameter.Tapir.route ~ GetOneQueryParameterAndError.Akka.route ~ GetOneQueryParameterAndError.Tapir.route ~ PostSimple.Tapir.route ~ PostSimple.Akka.route

    // Start API Rest
    Http()
      .newServerAt("0.0.0.0", 8080)
      .bind(routes)
      .onComplete {
        case Success(_) => println(s"Started!")
        case Failure(e) => println("Failed to start ... ", e)
      }
  }

}
