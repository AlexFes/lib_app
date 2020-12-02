package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class BookRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
    val dbConfig = dbConfigProvider.get[JdbcProfile]

    import dbConfig._
    import profile.api._

    class BooksTable(tag: Tag) extends Table[Book](tag, "books") {
        def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
        def title = column[String]("title")
        def year = column[Int]("year")
        def genre = column[String]("genre")
        def * = (title, year, genre, id) <> ((Book.apply _).tupled, Book.unapply)
    }

    val books = TableQuery[BooksTable]

    class AuthorsTable(tag: Tag) extends Table[Author](tag, "authors") {
        def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
        def name = column[String]("name")
        def year = column[Int]("year")
        def * = (name, year, id) <> ((Author.apply _).tupled, Author.unapply)
    }

    val authors = TableQuery[AuthorsTable]

    class BookAuthorRelation(tag: Tag) extends Table[(Long, Long)](tag, "relation") {
        def book = column[Long]("book")
        def author = column[Long]("author")

        def bookFK = foreignKey("book_FK", book, books)(_.id,
            onDelete = ForeignKeyAction.Cascade
        )
        def authorFK = foreignKey("author_FK", author, authors)(_.id,
            onDelete = ForeignKeyAction.Cascade
        )

        def * = (book, author)
    }

    val relation = TableQuery[BookAuthorRelation]

    def getBooks(ids: Seq[Long] = Seq(), limit: Int = 10, offset: Int = 0): Future[Seq[Book]] = ids match {
        case Seq() => db.run(books.drop(offset).take(limit).result)
        case s: Seq[Long] => db.run(books.filter(_.id inSet s).result)
    }

    def getForAuthors(ids: Seq[Long]) = {
        val join = relation.filter(_.author inSet ids)
          .join(books).on(_.book === _.id).result

        db.run(join).map { result =>
            result.groupBy(_._2.id).toVector.map {
                case (_, books) => books.map(_._1._2) -> books.head._2
            }
        }
    }

    def getAuthors(ids: Seq[Long] = Seq(), limit: Int = 10, offset: Int = 0): Future[Seq[Author]] = ids match {
        case Seq() => db.run(authors.drop(offset).take(limit).result)
        case s: Seq[Long] => db.run(authors.filter(_.id inSet s).result)
    }

    def getForBooks(ids: Seq[Long]) = {
        val join = relation.filter(_.book inSet ids)
          .join(authors).on(_.author === _.id).result

        db.run(join).map { result =>
            result.groupBy(_._2.id).toVector.map {
                case (_, authors) => authors.map(_._1._1) -> authors.head._2
            }
        }
    }

    def createAuthor(authorForm: AuthorInput) = db.run {
        authors returning authors.map(_.id) into ((author, id) => Author(author.name, author.year, id)) += Author(authorForm.name, authorForm.year)
    }

    def createBook(bookForm: BookInput) = {
        val res = for {
            bookId <- books.returning(books.map(_.id)) += Book(bookForm.title, bookForm.year, bookForm.genre)
            _ <- relation ++= bookForm.authors.map((bookId, _))
        } yield Book(bookForm.title, bookForm.year, bookForm.genre, bookId)

        db.run(res.transactionally)
    }

    def createSchema = db.run {
        books.schema.create andThen
        authors.schema.create andThen
        relation.schema.create
    }
}
