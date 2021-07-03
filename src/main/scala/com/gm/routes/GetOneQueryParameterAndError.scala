package com.gm.routes

import scala.concurrent.Future

object GetOneQueryParameterAndError {

  object Akka {
    import akka.http.scaladsl.model.StatusCodes
    import akka.http.scaladsl.server.Directives._
    import akka.http.scaladsl.server.Route

    // GET /calc with akka http
    val route: Route = path("calc") {
      parameter("number") { numberStr =>
        get {
          if (numberStr.isEmpty || numberStr.toLong < 0) {
            complete(StatusCodes.BadRequest, s"Invalid number $numberStr (akka)")
          } else {
            complete(StatusCodes.OK, s"Calculus ${numberStr.toLong * 2} (akka)")
          }
        }
      }
    }
  }

  object Tapir {
    import akka.http.scaladsl.server.Route
    import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
    import sttp.tapir.{endpoint, query}
    import sttp.tapir.{stringBody, Endpoint}

    /*
  Endpoint[I, E, O, -R]
    (input: EndpointInput[I], errorOutput: EndpointOutput[E], output: EndpointOutput[O], info: EndpointInfo)
      I Input parameter types.
      E Error output parameter types.
      O Output parameter types.
      R The capabilities that are required by this endpoint's inputs/outputs.
        This might be `Any` (no requirements)

     */

    // GET /calc2 identical to GET /hello using Tapir
    private def calculus(number: Long) = {
      if (number < 0) {
        Future.successful(Left(s"Invalid number $number (tapir)"))
      } else {
        Future.successful(Right(s"Calculus ${number * 2} (tapir)"))
      }
    }
    private val calculusEndpoint: Endpoint[Long, String, String, Any] =
      endpoint.get
        .in("calc2")
        .in(query[Long]("number"))
        .out(stringBody)
        .errorOut(stringBody)
    val route: Route =
      AkkaHttpServerInterpreter().toRoute(calculusEndpoint)(calculus)
  }

}
