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
            Field("year", IntType, resolve = _.value.year),
            Field("genre", StringType, resolve = _.value.genre)
        )
    )

    val AuthorType = ObjectType[Unit, Author](
        "Author",
        fields[Unit, Author](
            Field("id", LongType, resolve = _.value.id),
            Field("name", StringType, resolve = _.value.name),
            Field("year", IntType, resolve = _.value.year)
        )
    )

    val QueryType = ObjectType[BookRepository, Unit](
        "Query",
        fields[BookRepository, Unit](
            Field("allBooks", ListType(BookType), resolve = ctx => ctx.ctx.getBooks),
            Field("allAuthors", ListType(AuthorType), resolve = ctx => ctx.ctx.getAuthors)
        )
    )

    val BooksSchema = Schema(QueryType)
}
