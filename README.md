# akka-http-tapir

This is just a simple POC for using Tapir for fist time.
Each endpoint has been implemented twice:

- implementation using Akka Http 
- Implementation using Tapir

## GET /hello?name={some-name}

A very simple endpoint with takes a query parameter (`name`) and returns basically `Hello name!`.

To hit the endpoint implemented with Akka Http:

```
curl http://localhost:8080/hello?name="joe"
```

And for the endpoint implemented using Tapir:

```
curl http://localhost:8080/hello2?name="joe"
```

## GET /calc?number={some-number}

Similar to previous, but in this case the query parameter it's a number. If `number < 0` then it will returns `Invaldid number`. 
To hit the endpoint implemented with Akka Http:

```
curl http://localhost:8080/calc?number=10
```

And for the endpoint implemented using Tapir:

```
curl http://localhost:8080/calc2?number=10
```

## POST /person

This third example will accept a person as a body, will return a json, and if there's an error it will also return a json.
To hit the endpoint implemented with Akka Http:

```
curl -X POST http://localhost:8080/person -H "Content-Type: application/json"  -d '{"name":"John","age":10}'
```

And for the endpoint implemented using Tapir:

```
curl -X POST http://localhost:8080/person -H "Content-Type: application/json"  -d '{"name":"John","age":10}'
```

Both endpoints an object like this:

```
{
    "name" : "John",
    "age" : 10
}
```

And return an object like this:

```
{
    "id" : 343,
    "name" : "John",
    "age" : 10
}
```

## Tapir

Site: https://github.com/softwaremill/tapir

From the site: "With tapir, you can describe HTTP API endpoints as immutable Scala values".

The nice part is you can use Tapir to generate Open Api Specification automatically.

When you are defining a endpoint you are defining 4 important data:

- Input data types
- Error output data type
- Output data type
- The capabilities that are required by this endpoint inputs/outputs

In `GetOneQueryParameterAndError` object our Tapir endpoint has been defined like this:

```
private val calculusEndpoint: Endpoint[Long, String, String, Any] = ...
```

First parameter of Endpoint is `Long` because of: 

```
.in(query[Long]("number"))
```

In case we have more input parameters (in this case there's onle one) we must use tuple data type.

Second parameter is `String` because of:

```
.errorOut(stringBody)
```

The third parameter is `String` because of:

```
.out(stringBody)
```

In case of fourth parameter it will be discussed later, for one it's just `Any`.

