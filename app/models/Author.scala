package models

import play.api.libs.json._

case class Author(name: String, year: Int, id: Long = 0L)
case class AuthorInput(name: String, year: Int)