// @GENERATOR:play-routes-compiler
// @SOURCE:/home/alexfes/Documents/scala_app/conf/routes
// @DATE:Fri Nov 27 14:01:42 MSK 2020

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  BookController_0: controllers.BookController,
  // @LINE:15
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    BookController_0: controllers.BookController,
    // @LINE:15
    Assets_1: controllers.Assets
  ) = this(errorHandler, BookController_0, Assets_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, BookController_0, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.BookController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """schema""", """controllers.BookController.setSchema"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """graphql""", """controllers.BookController.graphql"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """graphql""", """controllers.BookController.graphqlBody"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_BookController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_BookController_index0_invoker = createInvoker(
    BookController_0.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.BookController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ Home page""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_BookController_setSchema1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("schema")))
  )
  private[this] lazy val controllers_BookController_setSchema1_invoker = createInvoker(
    BookController_0.setSchema,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.BookController",
      "setSchema",
      Nil,
      "GET",
      this.prefix + """schema""",
      """POST    /book                     controllers.BookController.addBook
GET     /books                    controllers.BookController.getBooks""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_BookController_graphql2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("graphql")))
  )
  private[this] lazy val controllers_BookController_graphql2_invoker = createInvoker(
    BookController_0.graphql,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.BookController",
      "graphql",
      Nil,
      "GET",
      this.prefix + """graphql""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_BookController_graphqlBody3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("graphql")))
  )
  private[this] lazy val controllers_BookController_graphqlBody3_invoker = createInvoker(
    BookController_0.graphqlBody,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.BookController",
      "graphqlBody",
      Nil,
      "POST",
      this.prefix + """graphql""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_Assets_versioned4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned4_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_BookController_index0_route(params@_) =>
      call { 
        controllers_BookController_index0_invoker.call(BookController_0.index)
      }
  
    // @LINE:9
    case controllers_BookController_setSchema1_route(params@_) =>
      call { 
        controllers_BookController_setSchema1_invoker.call(BookController_0.setSchema)
      }
  
    // @LINE:11
    case controllers_BookController_graphql2_route(params@_) =>
      call { 
        controllers_BookController_graphql2_invoker.call(BookController_0.graphql)
      }
  
    // @LINE:12
    case controllers_BookController_graphqlBody3_route(params@_) =>
      call { 
        controllers_BookController_graphqlBody3_invoker.call(BookController_0.graphqlBody)
      }
  
    // @LINE:15
    case controllers_Assets_versioned4_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned4_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
