package models

import play.api.libs.json._

case class Book(id: Long, title: String, bookYear: Int, genre: String, author: String, authorYear: Int)

object Book {
    implicit val bookFormat: OFormat[Book] = Json.format[Book]
}
