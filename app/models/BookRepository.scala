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

    private class BooksTable(tag: Tag) extends Table[Book](tag, "library") {
        def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
        def title = column[String]("title")
        def book_year = column[Int]("book_year")
        def genre = column[String]("genre")
        def author = column[String]("author")
        def author_year = column[Int]("author_year")
        def * = (id, title, book_year, genre, author, author_year) <> ((Book.apply _).tupled, Book.unapply)
    }

    /**
     * The starting point for all queries on the people table.
     */
    private val books = TableQuery[BooksTable]

    def create(title: String, book_year: Int, genre: String, author: String, author_year: Int) = db.run {
        (books.map(b => (b.title, b.book_year, b.genre, b.author, b.author_year))
          // get id generated for the book
          returning books.map(_.id)
          // combine original parameters with the new id
          into ((bookInfo, id) => Book(id, bookInfo._1, bookInfo._2, bookInfo._3, bookInfo._4, bookInfo._5))
          // insert in db
        ) += (title, book_year, genre, author, author_year)
    }

    /**
     * List all books
     */
    def list(): Future[Seq[Book]] = db.run {
        books.result
    }
}
