package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class PageRequestStreamFixture {
  val stream = new PageRequestStream(3)

  stream.clear

  stream.addPageRequest(2);
  stream.addPageRequest(3);
  stream.addPageRequest(1);
  stream.addPageRequest(3);
  stream.addPageRequest(4);
  stream.addPageRequest(3);
  stream.addPageRequest(5);
  stream.addPageRequest(4);
}

class PageRequestStream_Test extends AnyFunSpec with Matchers {
  describe("The PageRequestStream") {
    describe("knows about the requested pages") {
      it("shows the page request stream") {
        val stream = new PageRequestStream(2)
        stream.clear
        stream.addPageRequest(2);

        val expectedResult: String =
          s"Page Request Stream: 2"
        stream.show shouldBe expectedResult
      }
      it("can request multiple pages") {
        val stream = new PageRequestStream(0)
        stream.clear
        stream.addPageRequest(2);
        stream.addPageRequest(3);
        stream.addPageRequest(1);

        val expectedResult0: String =
        s"Page Request Stream: 2, 3, 1"
        stream.show shouldBe expectedResult0
      }
    }
  }
}
