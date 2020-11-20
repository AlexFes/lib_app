package models

import sangria.schema.{Field, ListType, ObjectType}
import sangria.schema._
import sangria.macros.derive._

object GraphqlSchema {
    val BookType = ObjectType[Unit, Book](
        "Book",
        fields[Unit, Book](
            Field("id", LongType, resolve = _.value.id),
            Field("title", StringType, resolve = _.value.title),
            Field("bookYear", IntType, resolve = _.value.bookYear),
            Field("genre", StringType, resolve = _.value.genre),
            Field("author", StringType, resolve = _.value.author),
            Field("authorYear", IntType, resolve = _.value.authorYear)
        )
    )

    val QueryType = ObjectType[BookRepository, Unit](
        "Query",
        fields[BookRepository, Unit](
            Field("allBooks", ListType(BookType), resolve = ctx => ctx.ctx.list())
        )
    )

    val BooksSchema = Schema(QueryType)
}
