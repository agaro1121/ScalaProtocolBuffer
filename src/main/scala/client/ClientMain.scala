package client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.japi.function.Procedure
import akka.stream.ActorMaterializer
import akka.util.ByteString
import models.Person.Person

import scala.concurrent.Future
import scala.concurrent.duration._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

/**
  * Created by anthonygaro on 8/22/16.
  */
object ClientMain extends App {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val exc = system.dispatcher

  val responseFuture: Future[HttpResponse] =
    Http().singleRequest(HttpRequest(uri = "http://localhost:9000/person"))

  val strictResponse = responseFuture.flatMap(_.entity.toStrict(3 seconds))

  val transformedResponse: Future[Person] = strictResponse.map { e ⇒
    Person.parseFrom(e.data.toArray)
  }

  transformedResponse.onComplete { triedPerson ⇒
    triedPerson.map { person ⇒
      println(person.asJson.noSpaces)
    }
  }

}
