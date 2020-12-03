package models

import sangria.schema.{Field, ListType, ObjectType}
import sangria.execution.deferred.{DeferredResolver, Fetcher, HasId, Relation, RelationIds}
import sangria.macros.derive._
import sangria.marshalling.FromInput
import sangria.schema._
import sangria.marshalling.sprayJson._
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

object GraphqlSchema {
    val GenreEnum = EnumType("Genre",
        values = List(
            EnumValue("DRAMA", value = "Drama"),
            EnumValue("ADVENTURE", value = "Adventure"),
            EnumValue("HORROR", value = "Horror")
        )
    )

    lazy val BookType: ObjectType[Unit, Book] = deriveObjectType[Unit, Book](
        ReplaceField("genre",
            Field("genre", GenreEnum, resolve = _.value.genre)
        ),
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
            Field("allBooks",
                ListType(BookType),
                arguments = List(Argument("limit", OptionInputType(IntType), 10), Argument("offset", OptionInputType(IntType), 0)),
                resolve = c => c.ctx.getBooks(limit = c.arg[Int]("limit"), offset = c.arg[Int]("offset"))
            ),
            Field("booksById",
                ListType(BookType),
                arguments = List(Argument("ids", ListInputType(LongType))),
                resolve = c => booksFetcher.deferSeq(c.arg[Seq[Long]]("ids"))
            ),
            Field("allAuthors",
                ListType(AuthorType),
                arguments = List(Argument("limit", OptionInputType(IntType), 10), Argument("offset", OptionInputType(IntType), 0)),
                resolve = c => c.ctx.getAuthors(limit = c.arg[Int]("limit"), offset = c.arg[Int]("offset"))
            ),
            Field("authorsById",
                ListType(AuthorType),
                arguments = List(Argument("ids", ListInputType(LongType))),
                resolve = c => authorsFetcher.deferSeq(c.arg[Seq[Long]]("ids"))
            )
        )
    )

    implicit val authorInputFormat: RootJsonFormat[AuthorInput] = jsonFormat2(AuthorInput)
    lazy val AuthorInputType: InputObjectType[AuthorInput] = deriveInputObjectType[AuthorInput]()

    implicit val bookInputFormat: RootJsonFormat[BookInput] = jsonFormat4(BookInput)
    lazy val BookInputType: InputObjectType[BookInput] = deriveInputObjectType[BookInput]()

    val Mutation = ObjectType[BookRepository, Unit](
        "Mutation",
        fields[BookRepository, Unit](
            Field("createAuthor",
                AuthorType,
                arguments = List(Argument("authorForm", AuthorInputType)),
                resolve = c => c.ctx.createAuthor(c.arg[AuthorInput]("authorForm"))
            ),
            Field("createBook",
                BookType,
                arguments = List(Argument("bookForm", BookInputType)),
                resolve = c => c.ctx.createBook(c.arg[BookInput]("bookForm"))
            ),
            Field("updateAuthor",
                IntType,
                arguments = List(
                    Argument("id", LongType),
                    Argument("authorForm", AuthorInputType)
                ),
                resolve = c => c.ctx.updateAuthor(c.arg[Long]("id"), c.arg[AuthorInput]("authorForm"))
            ),
            Field("updateBook",
                IntType,
                arguments = List(
                    Argument("id", LongType),
                    Argument("bookForm", BookInputType)
                ),
                resolve = c => c.ctx.updateBook(c.arg[Long]("id"), c.arg[BookInput]("bookForm"))
            ),
            Field("deleteAuthor",
                IntType,
                arguments = List(Argument("id", LongType)),
                resolve = c => c.ctx.deleteAuthor(c.arg[Long]("id"))
            ),
            Field("deleteBook",
                IntType,
                arguments = List(Argument("id", LongType)),
                resolve = c => c.ctx.deleteBook(c.arg[Long]("id"))
            )
        )
    )

    val BooksSchema = Schema(QueryType, Some(Mutation))
}
