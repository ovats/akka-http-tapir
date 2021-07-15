package com.gm

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.gm.routes.{GetOneQueryParameter, GetOneQueryParameterAndError, OpenAPIDocGeneration, Person, PostSimple}

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
    val routes                    = oneQueryParameter ~ oneQueryParameterAndError ~ postSimple

    //This will print a YAML with OAS specification
    import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
    import sttp.tapir.openapi.circe.yaml.RichOpenAPI
    val endpoints = Seq(
      GetOneQueryParameter.Tapir.endpointDoc,
      PostSimple.Tapir.endpointDoc,
      GetOneQueryParameterAndError.Tapir.calculusEndpoint,
    )
    println(OpenAPIDocsInterpreter().toOpenAPI(endpoints, title = "akka-http-tapir", "1.0").toYaml)

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
