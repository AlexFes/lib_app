package models

import play.api.libs.json._

case class Book(id: Long, title: String, year: Int, genre: String)

object Book {
    implicit val bookFormat: OFormat[Book] = Json.format[Book]
}
