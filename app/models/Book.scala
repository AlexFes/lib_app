package models

import play.api.libs.json._

case class Book(title: String, year: Int, genre: String, id: Long = 0L)
case class BookInput(title: String, year: Int, genre: String, authors: Seq[Long])

object Book {
    implicit val bookFormat: OFormat[Book] = Json.format[Book]
}
