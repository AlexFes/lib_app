// @GENERATOR:play-routes-compiler
// @SOURCE:/home/alexfes/Documents/scala_app/conf/routes
// @DATE:Fri Nov 27 14:01:42 MSK 2020

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:6
package controllers {

  // @LINE:6
  class ReverseBookController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:9
    def setSchema(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "schema")
    }
  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
    // @LINE:11
    def graphql(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "graphql")
    }
  
    // @LINE:12
    def graphqlBody(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "graphql")
    }
  
  }

  // @LINE:15
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:15
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
