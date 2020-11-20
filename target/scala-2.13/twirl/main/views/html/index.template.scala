
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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[Form[CreateBookForm],MessagesRequestHeader,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(book: Form[CreateBookForm])(implicit request: MessagesRequestHeader):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import helper._


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*4.1*/("""
"""),_display_(/*5.2*/request/*5.9*/.flash.get("success").map/*5.34*/ { key =>_display_(Seq[Any](format.raw/*5.43*/("""
   """),_display_(/*6.5*/request/*6.12*/.messages(key)),format.raw/*6.26*/("""
""")))}),format.raw/*7.2*/("""

"""),_display_(/*9.2*/main("Library App")/*9.21*/ {_display_(Seq[Any](format.raw/*9.23*/("""
  """),_display_(/*10.4*/form(routes.BookController.addBook())/*10.41*/ {_display_(Seq[Any](format.raw/*10.43*/("""
		"""),_display_(/*11.4*/inputText(book("title"))),format.raw/*11.28*/("""
		"""),_display_(/*12.4*/inputText(book("bookYear"))),format.raw/*12.31*/("""
	  	"""),_display_(/*13.6*/inputText(book("genre"))),format.raw/*13.30*/("""
	  	"""),_display_(/*14.6*/inputText(book("author"))),format.raw/*14.31*/("""
	  	"""),_display_(/*15.6*/inputText(book("authorYear"))),format.raw/*15.35*/("""
		"""),_display_(/*16.4*/CSRF/*16.8*/.formField),format.raw/*16.18*/("""

		"""),format.raw/*18.3*/("""<div class="buttons">
			<input type="submit" value="Add Book"/>
		</div>
	""")))}),format.raw/*21.3*/("""
""")))}),format.raw/*22.2*/("""
"""))
      }
    }
  }

  def render(book:Form[CreateBookForm],request:MessagesRequestHeader): play.twirl.api.HtmlFormat.Appendable = apply(book)(request)

  def f:((Form[CreateBookForm]) => (MessagesRequestHeader) => play.twirl.api.HtmlFormat.Appendable) = (book) => (request) => apply(book)(request)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2020-11-19T19:47:50.148
                  SOURCE: /home/alexfes/Documents/scala_app/app/views/index.scala.html
                  HASH: e9c1ea335937037e359eb8a1f617dca356f38622
                  MATRIX: 765->1|907->73|951->71|978->89|1005->91|1019->98|1052->123|1098->132|1128->137|1143->144|1177->158|1208->160|1236->163|1263->182|1302->184|1332->188|1378->225|1418->227|1448->231|1493->255|1523->259|1571->286|1603->292|1648->316|1680->322|1726->347|1758->353|1808->382|1838->386|1850->390|1881->400|1912->404|2018->480|2050->482
                  LINES: 21->1|24->3|27->2|28->4|29->5|29->5|29->5|29->5|30->6|30->6|30->6|31->7|33->9|33->9|33->9|34->10|34->10|34->10|35->11|35->11|36->12|36->12|37->13|37->13|38->14|38->14|39->15|39->15|40->16|40->16|40->16|42->18|45->21|46->22
                  -- GENERATED --
              */
          