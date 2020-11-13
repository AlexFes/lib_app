package controllers

import javax.inject._

import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class BookController @Inject()(repo: BookRepository,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

    /**
     * The mapping for the book form.
     */
    val bookForm: Form[CreateBookForm] = Form {
        mapping(
            "title" -> nonEmptyText,
            "book_year" -> number,
            "genre" -> nonEmptyText,
            "author" -> nonEmptyText,
            "author_year" -> number
        )(CreateBookForm.apply)(CreateBookForm.unapply)
    }

    def index = Action { implicit request =>
        Ok(views.html.index(bookForm))
    }

    /**
     * The add book action.
     *
     * This is asynchronous, since we're invoking the asynchronous methods on BookRepository.
     */
    def addBook = Action.async { implicit request =>
        // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle success
        bookForm.bindFromRequest.fold(
            // error case
            errorForm => {
                Future.successful(Ok(views.html.index(errorForm)))
            },
            // create the book
            book => {
                repo.create(book.title, book.book_year, book.genre, book.author, book.author_year).map { _ =>
                    // redirect to the index page
                    Redirect(routes.BookController.index).flashing("success" -> "book.created")
                }
            }
        )
    }

    def getBooks = Action.async { implicit request =>
        repo.list().map { books =>
            Ok(Json.toJson(books))
        }
    }

    def setSchema = Action.async { implicit request =>
        repo.createSchema.map { _ =>  Redirect(routes.BookController.index).flashing("success" -> "schema.created") }
    }
}

case class CreateBookForm(title: String, book_year: Int, genre: String, author: String, author_year: Int)

