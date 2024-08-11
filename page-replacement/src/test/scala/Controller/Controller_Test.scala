package controller

import model._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class PageRequestStreamFixture {
  val stream = new PageRequestStream(0)
  stream.init
  stream.addPageRequest(2);
  stream.addPageRequest(3);
  stream.addPageRequest(1);
  stream.addPageRequest(3);
  stream.addPageRequest(4);
  stream.addPageRequest(3);
  stream.addPageRequest(5);
  stream.addPageRequest(4);

}

class Controller_Test extends AnyFunSpec with Matchers {
  describe("The Controller") {
    describe("knows about adding requested pages to the stream") {
      it("shows the page request stream") {
        val stream = new PageRequestStream(0)
        stream.clear
        stream.addPageRequest(2);
        stream.addPageRequest(3);
        stream.addPageRequest(1);
        stream.addPageRequest(3);
        stream.addPageRequest(4);
        stream.addPageRequest(3);
        stream.addPageRequest(5);
        stream.addPageRequest(4);

        val expectedResult: String =
          s"Page Request Stream: 2, 3, 1, 3, 4, 3, 5, 4"
        stream.show shouldBe expectedResult
      }
    }
  }
}
