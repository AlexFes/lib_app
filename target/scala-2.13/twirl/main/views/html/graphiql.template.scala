
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

object graphiql extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""<!--
 *  Copyright (c) 2020 GraphQL Contributors
 *  All rights reserved.
 *
 *  This source code is licensed under the license found in the
 *  LICENSE file in the root directory of this source tree.
-->
<!DOCTYPE html>
<html>
    <head>
        <style>
                body """),format.raw/*13.22*/("""{"""),format.raw/*13.23*/("""
                    """),format.raw/*14.21*/("""height: 100%;
                    margin: 0;
                    width: 100%;
                    overflow: hidden;
                """),format.raw/*18.17*/("""}"""),format.raw/*18.18*/("""

                """),format.raw/*20.17*/("""#graphiql """),format.raw/*20.27*/("""{"""),format.raw/*20.28*/("""
                    """),format.raw/*21.21*/("""height: 100vh;
                """),format.raw/*22.17*/("""}"""),format.raw/*22.18*/("""
        """),format.raw/*23.9*/("""</style>

            <!--
      This GraphiQL example depends on Promise and fetch, which are available in
      modern browsers, but can be "polyfilled" for older browsers.
      GraphiQL itself depends on React DOM.
      If you do not want to rely on a CDN, you can host these files locally or
      include them directly in your favored resource bunder.
    -->
        <script
        crossorigin
        src="https://unpkg.com/react@16/umd/react.development.js"
        ></script>
        <script
        crossorigin
        src="https://unpkg.com/react-dom@16/umd/react-dom.development.js"
        ></script>

            <!--
      These two files can be found in the npm module, however you may wish to
      copy them directly into your environment, or perhaps include them in your
      favored resource bundler.
     -->
        <link rel="stylesheet" href="https://unpkg.com/graphiql/graphiql.min.css" />
    </head>

    <body>
        <div id="graphiql">Loading...</div>
        <script
        src="https://unpkg.com/graphiql/graphiql.min.js"
        type="application/javascript"
        ></script>
        <script src="/renderExample.js" type="application/javascript"></script>
        <script>
                function graphQLFetcher(graphQLParams) """),format.raw/*57.56*/("""{"""),format.raw/*57.57*/("""
                    """),format.raw/*58.21*/("""return fetch(
                            // 'https://swapi-graphql.netlify.com/.netlify/functions/index',
                            '/graphql',
                            """),format.raw/*61.29*/("""{"""),format.raw/*61.30*/("""
                                """),format.raw/*62.33*/("""method: 'post',
                                headers: """),format.raw/*63.42*/("""{"""),format.raw/*63.43*/("""
                                    """),format.raw/*64.37*/("""Accept: 'application/json',
                                    'Content-Type': 'application/json',
                                """),format.raw/*66.33*/("""}"""),format.raw/*66.34*/(""",
                                body: JSON.stringify(graphQLParams),
                                credentials: 'omit',
                            """),format.raw/*69.29*/("""}"""),format.raw/*69.30*/(""",
                    ).then(function (response) """),format.raw/*70.48*/("""{"""),format.raw/*70.49*/("""
                        """),format.raw/*71.25*/("""return response.json().catch(function () """),format.raw/*71.66*/("""{"""),format.raw/*71.67*/("""
                            """),format.raw/*72.29*/("""return response.text();
                        """),format.raw/*73.25*/("""}"""),format.raw/*73.26*/(""");
                    """),format.raw/*74.21*/("""}"""),format.raw/*74.22*/(""");
                """),format.raw/*75.17*/("""}"""),format.raw/*75.18*/("""

                """),format.raw/*77.17*/("""ReactDOM.render(
                        React.createElement(GraphiQL, """),format.raw/*78.55*/("""{"""),format.raw/*78.56*/("""
                            """),format.raw/*79.29*/("""fetcher: graphQLFetcher,
                            defaultVariableEditorOpen: true,
                        """),format.raw/*81.25*/("""}"""),format.raw/*81.26*/("""),
                        document.getElementById('graphiql'),
                );
        </script>
    </body>
</html>"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2020-11-20T09:04:28.147
                  SOURCE: /home/alexfes/Documents/scala_app/app/views/graphiql.scala.html
                  HASH: 1d2372846d4a4b9ee5b00d2e60bda5c98c5661e9
                  MATRIX: 725->1|821->4|1125->280|1154->281|1203->302|1363->434|1392->435|1438->453|1476->463|1505->464|1554->485|1613->516|1642->517|1678->526|2975->1797|3004->1798|3053->1819|3256->1994|3285->1995|3346->2028|3431->2085|3460->2086|3525->2123|3685->2255|3714->2256|3894->2408|3923->2409|4000->2458|4029->2459|4082->2484|4151->2525|4180->2526|4237->2555|4313->2603|4342->2604|4393->2627|4422->2628|4469->2647|4498->2648|4544->2666|4643->2737|4672->2738|4729->2767|4867->2877|4896->2878
                  LINES: 21->1|26->2|37->13|37->13|38->14|42->18|42->18|44->20|44->20|44->20|45->21|46->22|46->22|47->23|81->57|81->57|82->58|85->61|85->61|86->62|87->63|87->63|88->64|90->66|90->66|93->69|93->69|94->70|94->70|95->71|95->71|95->71|96->72|97->73|97->73|98->74|98->74|99->75|99->75|101->77|102->78|102->78|103->79|105->81|105->81
                  -- GENERATED --
              */
          