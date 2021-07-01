package com.gm

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route

import scala.util.{Failure, Success}

object TairSample {

  def main(args: Array[String]): Unit = {
    println("Starting AkkaHttpService ...")

    implicit val system: ActorSystem = ActorSystem()
    import system.dispatcher

    // Just one route
    val routes: Route = path("hello") {
      get {
        complete(StatusCodes.OK, "Hello world!")
      }
    }

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
