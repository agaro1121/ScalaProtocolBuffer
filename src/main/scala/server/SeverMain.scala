package server

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.{Marshal, Marshaller, ToResponseMarshallable}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, ResponseEntity}
import akka.http.scaladsl.{Http, marshalling}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import models.Person.Person


/**
  * Created by anthonygaro on 8/22/16.
  */
object SeverMain extends App {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val route =
    path("person") {
      get {
        val person: Person = Person(Some("Anthony"), Some(27), Nil)
        println(person.toByteArray.mkString)
        complete( HttpResponse(entity = HttpEntity(ContentTypes.`application/octet-stream`, person.toByteArray)) )
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 9000)
  println(s"Server online at http://localhost:9000/\nPress RETURN to stop...")

}
