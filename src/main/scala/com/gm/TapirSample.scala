package com.gm

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import akka.http.scaladsl.server.Route
import sttp.tapir._
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter

import scala.concurrent.Future
import scala.util.{Failure, Success}

object TapirSample {

  def main(args: Array[String]): Unit = {
    println("Starting TapirSample ...")

    implicit val system: ActorSystem = ActorSystem()
    import system.dispatcher

    // GET /hello with akka http
    val routesAkkaHttp: Route = path("hello") {
      parameter("name") { name =>
        get {
          complete(StatusCodes.OK, s"Hello $name!")
        }
      }
    }

    // GET /hello2 identical to GET /hello using Tapir
    val tapirDefinition = endpoint.get.in("hello2").in(query[String]("name")).out(stringBody)
    val routesTapir: Route =
      AkkaHttpServerInterpreter().toRoute(tapirDefinition)(name => Future.successful(Right(s"Hello $name!")))

    // Start API Rest
    Http()
      .newServerAt("0.0.0.0", 8080)
      .bind(routesAkkaHttp ~ routesTapir)
      .onComplete {
        case Success(_) => println(s"Started!")
        case Failure(e) => println("Failed to start ... ", e)
      }
  }

}
