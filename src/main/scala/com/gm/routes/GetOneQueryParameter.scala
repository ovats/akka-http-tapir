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
          complete(StatusCodes.OK, s"Hello $name!")
        }
      }
    }
  }

  object Tapir {
    import akka.http.scaladsl.server.Route
    import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
    import sttp.tapir.{endpoint, query, stringBody}

    // GET /hello2 identical to GET /hello using Tapir
    private val tapirDefinition = endpoint.get.in("hello2").in(query[String]("name")).out(stringBody)
    val route: Route =
      AkkaHttpServerInterpreter().toRoute(tapirDefinition)(name => Future.successful(Right(s"Hello $name!")))
  }

}
