
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
                  DATE: 2020-11-23T17:41:01.984
                  SOURCE: /home/alexfes/Documents/scala_app/app/views/index.scala.html
                  HASH: df7d8dbd821224cb344d6b4c025c63c9be70b75b
                  MATRIX: 765->1|907->73|951->71|978->89|1005->91|1019->98|1052->123|1098->132|1128->137|1143->144|1177->158|1208->160
                  LINES: 21->1|24->3|27->2|28->4|29->5|29->5|29->5|29->5|30->6|30->6|30->6|31->7
                  -- GENERATED --
              */
          