package com.gm.routes

import sttp.tapir.Endpoint
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.openapi.circe.yaml._

object OpenAPIDocGeneration {

  // This is a very generic documentation of an endpoint
  def getYamlDocAsString[I, E, O, S](
      title: String = "Title",
      version: String = "1.0",
      e: Endpoint[I, E, O, S],
  ): String = {
    val docs = OpenAPIDocsInterpreter().toOpenAPI(e, title, version)
    docs.toYaml
  }

}
