package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ Future, ExecutionContext }

@Singleton
class BookRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
    // JdbcProfile for this provider
    private val dbConfig = dbConfigProvider.get[JdbcProfile]

    // brings db into scope, for actual db operations
    // brings the Slick DSL into scope, to define the table and other queries
    import dbConfig._
    import profile.api._

    private class BooksTable(tag: Tag) extends Table[Book](tag, "books") {
        def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
        def title = column[String]("title")
        def year = column[Int]("year")
        def genre = column[String]("genre")
        def * = (title, year, genre, id) <> ((Book.apply _).tupled, Book.unapply)
    }

    private class AuthorsTable(tag: Tag) extends Table[Author](tag, "authors") {
        def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
        def name = column[String]("name")
        def year = column[Int]("year")
        def * = (name, year, id) <> ((Author.apply _).tupled, Author.unapply)
    }

    private class BookAuthorRelation(tag: Tag) extends Table[(Long, Long)](tag, "relation") {
        def book = column[Long]("book")
        def author = column[Long]("author")
        def * = (book, author)
    }

    private val books = TableQuery[BooksTable]
    private val authors = TableQuery[AuthorsTable]
    private val relation = TableQuery[BookAuthorRelation]

    def getBooks: Future[Seq[Book]] = db.run {
        books.result
    }

    def getAuthors: Future[Seq[Author]] = db.run {
        authors.result
    }

    def createAuthor(name: String, year: Int) = db.run {
        authors returning authors.map(_.id) into ((author, id) => Author(author.name, author.year, id)) += Author(name, year)
    }

    def createBook(title: String, year: Int, genre: String) = db.run {
        books returning books.map(_.id) into ((book, id) => Book(book.title, book.year, book.genre, id)) += Book(title, year, genre)
    }

    def createSchema = db.run {
        books.schema.create andThen
        authors.schema.create andThen
        relation.schema.create
    }
}
