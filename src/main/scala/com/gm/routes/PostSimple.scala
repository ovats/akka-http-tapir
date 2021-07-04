package com.gm.routes

import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import sttp.tapir.Endpoint

import scala.concurrent.Future

case class Person(id: Long, name: String, age: Int)
case class PersonRequest(name: String, age: Int)

object PostSimple {

  object Akka extends FailFastCirceSupport {
    import akka.http.scaladsl.model.StatusCodes
    import akka.http.scaladsl.server.Directives._
    import akka.http.scaladsl.server.Route

    val route: Route = path("person") {
      post {
        entity(as[PersonRequest]) { personRequest =>
          complete(StatusCodes.OK, Person(41, personRequest.name, personRequest.age))
        }
      }
    }
  }

  object Tapir {
    import akka.http.scaladsl.server.Route
    import sttp.tapir.generic.auto._
    import sttp.tapir.json.circe._
    import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
    import sttp.tapir.{endpoint, stringBody}

    private def postPerson(p: PersonRequest) = {
      Future.successful(Right(Person(id = 43, p.name, p.age)))
    }

    val createPersonEndpoint: Endpoint[PersonRequest, String, Person, Any] =
      endpoint.post
        .in("person2")
        .in(jsonBody[PersonRequest])
        .out(jsonBody[Person])
        .errorOut(stringBody)

    val route: Route = AkkaHttpServerInterpreter().toRoute(createPersonEndpoint)(postPerson)
  }
}
