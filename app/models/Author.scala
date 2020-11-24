package models

import play.api.libs.json._

case class Author(name: String, year: Int, id: Long = 0L)
