package com.gm

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.gm.routes.{GetOneQueryParameter, GetOneQueryParameterAndError, PostSimple}
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.openapi.circe.yaml.RichOpenAPI
import sttp.tapir.swagger.akkahttp.SwaggerAkka

import scala.util.{Failure, Success}

object TapirSample {

  def main(args: Array[String]): Unit = {
    println("Starting TapirSample ...")

    implicit val system: ActorSystem = ActorSystem()
    import system.dispatcher

    // Routes
    val oneQueryParameter         = GetOneQueryParameter.Akka.route ~ GetOneQueryParameter.Tapir.route
    val oneQueryParameterAndError = GetOneQueryParameterAndError.Akka.route ~ GetOneQueryParameterAndError.Tapir.route
    val postSimple                = PostSimple.Tapir.route ~ PostSimple.Akka.route

    // List of endpoints with some documentation added.
    // The list of endpoint could be just the list of original endpoints.
    val endpoints = Seq(
      GetOneQueryParameter.Tapir.endpointDoc,
      PostSimple.Tapir.endpointDoc,
      GetOneQueryParameterAndError.Tapir.calculusEndpoint,
    )

    // Endpoint that will show Open API Specification
    val openAPIDocRoute = new SwaggerAkka(
      OpenAPIDocsInterpreter().toOpenAPI(endpoints, title = "akka-http-tapir", "1.0").toYaml
    ).routes

    // All routes
    val routes = oneQueryParameter ~ oneQueryParameterAndError ~ postSimple ~ openAPIDocRoute

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
