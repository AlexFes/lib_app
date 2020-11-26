package models

import sangria.schema.{Field, ListType, ObjectType}
import sangria.execution.deferred.{DeferredResolver, Fetcher, HasId, Relation, RelationIds}
import sangria.macros.derive._
import sangria.schema._

object GraphqlSchema {
    lazy val BookType: ObjectType[Unit, Book] = deriveObjectType[Unit, Book](
        AddFields(
            Field("authors",
                ListType(AuthorType),
                resolve = c => {
                    authorsFetcher.deferRelSeqMany(
                        authorsForBooksRelation,
                        Seq[Long](c.value.id)
                    )
                }
            )
        )
    )

    lazy val AuthorType: ObjectType[Unit, Author] = deriveObjectType[Unit, Author](
        AddFields(
            Field("books",
                ListType(BookType),
                resolve = c => {
                    booksFetcher.deferRelSeqMany(
                        booksForAuthorsRelation,
                        Seq[Long](c.value.id)
                    )
                }
            )
        )
    )

    val booksForAuthorsRelation = Relation[Book, (Seq[Long], Book), Long]("forAuthors", _._1, _._2)
    val authorsForBooksRelation = Relation[Author, (Seq[Long], Author), Long]("forBooks", _._1, _._2)

    implicit val bookHasId = HasId[Book, Long](_.id)
    implicit val authorHasId = HasId[Author, Long](_.id)

    val booksFetcher: Fetcher[BookRepository, Book, (Seq[Long], Book), Long] = Fetcher.relCaching(
        (ctx: BookRepository, ids: Seq[Long]) => ctx.getBooks(ids),
        (ctx: BookRepository, ids: RelationIds[Book]) => ctx.getForAuthors(ids(booksForAuthorsRelation))
    )
    val authorsFetcher: Fetcher[BookRepository, Author, (Seq[Long], Author), Long] = Fetcher.relCaching(
        (ctx: BookRepository, ids: Seq[Long]) => ctx.getAuthors(ids),
        (ctx: BookRepository, ids: RelationIds[Author]) => ctx.getForBooks(ids(authorsForBooksRelation))
    )

    val Resolver = DeferredResolver.fetchers(booksFetcher, authorsFetcher)


    val QueryType = ObjectType[BookRepository, Unit](
        "Query",
        fields[BookRepository, Unit](
            Field("allBooks", ListType(BookType), resolve = c => c.ctx.getBooks()),
            Field("books",
                ListType(BookType),
                arguments = List(Argument("ids", ListInputType(LongType))),
                resolve = c => booksFetcher.deferSeq(c.arg[Seq[Long]]("ids"))
            ),
            Field("allAuthors", ListType(AuthorType), resolve = c => c.ctx.getAuthors()),
            Field("authors",
                ListType(AuthorType),
                arguments = List(Argument("ids", ListInputType(LongType))),
                resolve = c => authorsFetcher.deferSeq(c.arg[Seq[Long]]("ids"))
            )
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
