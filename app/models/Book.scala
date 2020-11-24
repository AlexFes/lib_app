package models

import play.api.libs.json._

case class Book(title: String, year: Int, genre: String, id: Long = 0L)

object Book {
    implicit val bookFormat: OFormat[Book] = Json.format[Book]
}
