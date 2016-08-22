/**
  * Created by anthonygaro on 8/22/16.
  */

import models.Person.Person
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object Main extends App {

  val p = Person(Some("Anthony"), Some(27), Nil)

  val p2 = Person().update(
    _.name := "Anthony",
    _.age  := 27
  )

  val pJson = p.asJson.noSpaces
  println(pJson)



}
