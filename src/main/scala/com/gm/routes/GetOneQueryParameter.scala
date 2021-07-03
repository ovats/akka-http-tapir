package com.gm.routes

import scala.concurrent.Future

object GetOneQueryParameter {

  object Akka {
    import akka.http.scaladsl.model.StatusCodes
    import akka.http.scaladsl.server.Directives._
    import akka.http.scaladsl.server.Route

    // GET /hello with akka http
    val route: Route = path("hello") {
      parameter("name") { name =>
        get {
          complete(StatusCodes.OK, s"Hello $name! (akka)")
        }
      }
    }
  }

  object Tapir {
    /*
      Endpoint[I, E, O, -R]
        (input: EndpointInput[I], errorOutput: EndpointOutput[E], output: EndpointOutput[O], info: EndpointInfo)
          I Input parameter types.
          E Error output parameter types.
          O Output parameter types.
          R The capabilities that are required by this endpoint's inputs/outputs.
            This might be `Any` (no requirements)

     */
    import akka.http.scaladsl.server.Route
    import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
    import sttp.tapir.{endpoint, query, stringBody, Endpoint}

    // GET /hello2 identical to GET /hello using Tapir
    private def helloName(name: String) = Future.successful(Right(s"Hello $name! (tapir)"))
    val helloNameEndpoint: Endpoint[String, Unit, String, Any] =
      endpoint.get
        .in("hello2")
        .in(query[String]("name"))
        .out(stringBody)
    val route: Route =
      AkkaHttpServerInterpreter().toRoute(helloNameEndpoint)(helloName)
  }

}
