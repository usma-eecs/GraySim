package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class OPT_Test extends AnyFunSpec with Matchers {

  describe("The OPT Page Replacement Algorithm") {
    describe("knows about page replacement in a OPT fashion") {
      it("shows basic OPT with 4 pages"){
        val stream = new PageRequestStream(0)
        stream.clear
        stream.addPageRequest(1);
        stream.addPageRequest(2);
        stream.addPageRequest(3);
        stream.addPageRequest(4);
        val foreknowledge = Array(1,2,3,4,2,3,1)
        val opt = new OPT(3)
        opt.addForeknowledge(foreknowledge)
        opt.init(stream.getPageRequestStream)

        val expectedResult: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 4\n" +
           "Frame 1: -, 2, 2, 2\n" +
           "Frame 2: -, -, 3, 3\n"

        opt.showMemory() shouldBe expectedResult
      }

      it("shows more complex OPT: most of CS481 ICE"){
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
        stream.addPageRequest(3);
        val foreknowledge = Array(2, 3, 1, 3, 4, 3, 5, 4, 3, 2, 1, 3, 6, 3)
        val opt1 = new OPT(3)
        opt1.addForeknowledge(foreknowledge)
        opt1.init(stream.getPageRequestStream)

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 2, 2, 2, 2, 2, 2, 5, 5, 5\n" +
           "Frame 1: -, 3, 3, 3, 3, 3, 3, 3, 3\n" +
           "Frame 2: -, -, 1, 1, 4, 4, 4, 4, 4\n"

        opt1.showMemory() shouldBe expectedResult0
      }

      it("shows more complex OPT: entire CS481 ICE, which includes ambiguity"){
        val stream1 = new PageRequestStream(0)
        val foreknowledge = Array(2, 3, 1, 3, 4, 3, 5, 4, 3, 2, 1, 3, 6, 3)
        val opt2 = new OPT(3)
        opt2.addForeknowledge(foreknowledge)
        opt2.init(stream1.getPageRequestStream)

        val expectedResult1: String =
          s"Current memory state:\n" +
           "Frame 0: 2, 2, 2, 2, 2, 2, 5, 5, 5, 2, 1, 1, 6, 6\n" +
           "Frame 1: -, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3\n" +
           "Frame 2: -, -, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4\n"

        opt2.showMemory() shouldBe expectedResult1
      }
      it("can do a page request stream that differs for all policies") {
        // Page Request Stream
        val stream1 = new PageRequestStream(2) // 1, 2, 3, 2, 1, 4, 2, 5, 6
        val foreknowledge = Array(1, 2, 3, 2, 1, 4, 2, 5, 6)
        val opt1 = new OPT(3)
        opt1.addForeknowledge(foreknowledge)
        opt1.init(stream1.getPageRequestStream)

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 1, 1, 4, 4, 5, 6\n" +
           "Frame 1: -, 2, 2, 2, 2, 2, 2, 2, 2\n" +
           "Frame 2: -, -, 3, 3, 3, 3, 3, 3, 3\n"
        opt1.showMemory() shouldBe expectedResult0
      }
      it("can do a page request stream that is the same for all policies") {
        // Page Request Stream
        val stream1 = new PageRequestStream(3) // 1, 2, 3, 2, 3, 4
        val foreknowledge = Array(1, 2, 3, 2, 3, 4)
        val opt1 = new OPT(3)
        opt1.addForeknowledge(foreknowledge)
        opt1.init(stream1.getPageRequestStream)

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 1, 1, 4\n" +
           "Frame 1: -, 2, 2, 2, 2, 2\n" +
           "Frame 2: -, -, 3, 3, 3, 3\n"
        opt1.showMemory() shouldBe expectedResult0
      }
    }
  }
}

