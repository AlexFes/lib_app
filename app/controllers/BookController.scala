package controllers

import javax.inject._
import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.{JsObject, JsString, Json}
import play.api.mvc._
import sangria.execution.deferred.DeferredResolver
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError, QueryReducer}
import sangria.parser.{QueryParser, SyntaxError}
import sangria.marshalling.playJson._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class BookController @Inject()(repo: BookRepository,
                                 cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {
    val bookForm: Form[CreateBookForm] = Form {
        mapping(
            "title" -> nonEmptyText,
            "bookYear" -> number,
            "genre" -> nonEmptyText,
            "author" -> nonEmptyText,
            "authorYear" -> number
        )(CreateBookForm.apply)(CreateBookForm.unapply)
    }

    def index = Action { implicit request =>
        // Ok(views.html.index(bookForm))
        Ok(views.html.graphiql())
    }

    def graphql = Action { implicit request =>
        Ok(views.html.graphiql())
    }

    def graphqlBody = Action.async(parse.json) { request =>
        val query = (request.body \ "query").as[String]
        val operation = (request.body \ "operationName").asOpt[String]

        val variables = (request.body \ "variables").toOption.flatMap {
            case JsString(vars) => Some(parseVariables(vars))
            case obj: JsObject => Some(obj)
            case _ => None
        }

        executeQuery(query, variables, operation)
    }

    private def parseVariables(variables: String) =
        if (variables.trim == "" || variables.trim == "null") Json.obj() else Json.parse(variables).as[JsObject]

    private def executeQuery(query: String, variables: Option[JsObject], operation: Option[String]) =
        QueryParser.parse(query) match {
            case Success(queryAst) =>
                Executor.execute(GraphqlSchema.BooksSchema, queryAst, repo,
                    operationName = operation,
                    variables = variables getOrElse Json.obj(),
                    deferredResolver = GraphqlSchema.Resolver
                )
                  .map(Ok(_))
                  .recover {
                      case error: QueryAnalysisError => BadRequest(error.resolveError)
                      case error: ErrorWithResolver => InternalServerError(error.resolveError)
                  }

            case Failure(error) =>
                throw error
        }

//    def getBooks = Action.async { implicit request =>
//        repo.getBooks().map { books =>
//            Ok(Json.toJson(books))
//        }
//    }

    def setSchema = Action.async { implicit request =>
        repo.createSchema.map { _ =>  Redirect(routes.BookController.index).flashing("success" -> "schema.created") }
    }
}

case class CreateBookForm(title: String, bookYear: Int, genre: String, author: String, authorYear: Int)

