package models

import sangria.schema.{Field, ListType, ObjectType}
import sangria.schema._

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
            Field("books", ListType(BookType), resolve = c => c.ctx.getBooks),
            Field("authors", ListType(AuthorType), resolve = c => c.ctx.getAuthors)
        )
    )

    val Mutation = ObjectType[BookRepository, Unit](
        "Mutation",
        fields[BookRepository, Unit](
            Field("createAuthor",
                AuthorType,
                arguments = List(Argument("name", StringType), Argument("year", IntType)),
                resolve = c => c.ctx.createAuthor(c.arg[String]("name"), c.arg[Int]("year"))
            ),
            Field("createBook",
                BookType,
                arguments = List(
                    Argument("title", StringType),
                    Argument("year", IntType),
                    Argument("genre", StringType),
                    Argument("authors", ListInputType(LongType))
                ),
                resolve = c => c.ctx.createBook(
                    c.arg[String]("title"),
                    c.arg[Int]("year"),
                    c.arg[String]("genre"),
                    c.arg[Seq[Long]]("authors")
                )
            )
        )
    )

    val BooksSchema = Schema(QueryType, Some(Mutation))
}
