package models

import play.api.libs.json._

case class Book(id: Long, title: String, book_year: Int, genre: String, author: String, author_year: Int)

object Book {
    implicit val bookFormat: OFormat[Book] = Json.format[Book]
}
