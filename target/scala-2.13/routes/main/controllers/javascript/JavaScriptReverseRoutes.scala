// @GENERATOR:play-routes-compiler
// @SOURCE:/home/alexfes/Documents/scala_app/conf/routes
// @DATE:Fri Nov 27 14:01:42 MSK 2020

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset

// @LINE:6
package controllers.javascript {

  // @LINE:6
  class ReverseBookController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def setSchema: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.BookController.setSchema",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "schema"})
        }
      """
    )
  
    // @LINE:6
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.BookController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
    // @LINE:11
    def graphql: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.BookController.graphql",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "graphql"})
        }
      """
    )
  
    // @LINE:12
    def graphqlBody: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.BookController.graphqlBody",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "graphql"})
        }
      """
    )
  
  }

  // @LINE:15
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:15
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }


}
