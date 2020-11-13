// @GENERATOR:play-routes-compiler
// @SOURCE:/home/alexfes/Documents/scala_app/conf/routes
// @DATE:Fri Nov 13 12:18:04 MSK 2020


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
