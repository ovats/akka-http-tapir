# akka-http-tapir

To hit the endpoint (`GET /hello`) implemented with Akka Http:

```
curl http://localhost:8080/hello?"joe"
```

And for the endpoint implemented using Tapir:

```
curl http://localhost:8080/hello2?"joe"
```

## Tapir

Site: https://github.com/softwaremill/tapir

From the site: "With tapir, you can describe HTTP API endpoints as immutable Scala values".

The nice part is you can use Tapir to generate Open Api Specification automatically.

