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
        def * = (id, title, year, genre) <> ((Book.apply _).tupled, Book.unapply)
    }

    private class AuthorsTable(tag: Tag) extends Table[Author](tag, "authors") {
        def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
        def name = column[String]("name")
        def year = column[Int]("year")
        def * = (id, name, year) <> ((Author.apply _).tupled, Author.unapply)
    }

    private class BookAuthorMap(tag: Tag) extends Table[(Long, Long)](tag, "map") {
        def book = column[Long]("book")
        def author = column[Long]("author")
        def * = (book, author)
    }

    private val books = TableQuery[BooksTable]
    private val authors = TableQuery[AuthorsTable]
    private val map = TableQuery[BookAuthorMap]

//    def create(title: String, bookYear: Int, genre: String, author: String, authorYear: Int) = db.run {
//        (books.map(b => (b.title, b.bookYear, b.genre, b.author, b.authorYear))
//          // get id generated for the book
//          returning books.map(_.id)
//          // combine original parameters with the new id
//          into ((bookInfo, id) => Book(id, bookInfo._1, bookInfo._2, bookInfo._3, bookInfo._4, bookInfo._5))
//          // insert in db
//        ) += (title, bookYear, genre, author, authorYear)
//    }

    def getBooks: Future[Seq[Book]] = db.run {
        books.result
    }

    def getAuthors: Future[Seq[Author]] = db.run {
        authors.result
    }

    def createSchema = db.run {
        books.schema.create andThen
        authors.schema.create andThen
        map.schema.create
    }
}
