package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class Clock_Test extends AnyFunSpec with Matchers {

  describe("The Clock Page Replacement Algorithm") {
    describe("knows about page replacement using athe Clock algorithm") {
      it("shows basic Clock with 4 pages"){
        val clock = new Clock(3)

        val stream = new PageRequestStream(0)
        stream.clear
        stream.addPageRequest(1);
        stream.addPageRequest(2);
        stream.addPageRequest(3);
        stream.addPageRequest(4);


        val expectedResult: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 4\n" +
           "Frame 1: -, 2, 2, 2\n" +
           "Frame 2: -, -, 3, 3\n"

        clock.init(stream.getPageRequestStream)
        clock.showMemory() shouldBe expectedResult
      }
      it("shows more complex clock: entire CS481 ICE"){
        val clock1 = new Clock(3)

        // Page Request Stream (2, 3, 1, 3, 4, 3, 5, 4, 3, 2, 1, 3, 6, 3)
        val stream1 = new PageRequestStream(0)

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 2, 2, 2, 2, 4, 4, 4, 4, 4, 2, 2, 2, 6, 6\n" +
           "Frame 1: -, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1\n" +
           "Frame 2: -, -, 1, 1, 1, 1, 5, 5, 5, 5, 5, 3, 3, 3\n"

        clock1.init(stream1.getPageRequestStream)
        clock1.showMemory() shouldBe expectedResult0
      }
      it("shows more complex clock: random test I made up"){
        val clock1 = new Clock(3)

        // Page Request Stream (1, 5, 3, 2, 4, 3, 5, 2, 1, 3, 2, 4, 3, 3)
        val stream1 = new PageRequestStream(5)

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3\n" +
           "Frame 1: -, 5, 5, 5, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4\n" +
           "Frame 2: -, -, 3, 3, 3, 3, 5, 5, 5, 5, 2, 2, 2, 2\n"

        clock1.init(stream1.getPageRequestStream)
        clock1.showMemory() shouldBe expectedResult0
      }
      it("can do a page request stream that differs for all policies") {
        val clock1 = new Clock(3)

        // Page Request Stream
        val stream1 = new PageRequestStream(2) // 1, 2, 3, 2, 1, 4, 2, 5, 6

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 1, 1, 4, 4, 4, 4\n" +
           "Frame 1: -, 2, 2, 2, 2, 2, 2, 2, 6\n" +
           "Frame 2: -, -, 3, 3, 3, 3, 3, 5, 5\n"
        clock1.init(stream1.getPageRequestStream)
        clock1.showMemory() shouldBe expectedResult0
      }
      it("can do a page request stream that is the same for all policies") {
        val clock1 = new Clock(3)

        // Page Request Stream
        val stream1 = new PageRequestStream(3) // 1, 2, 3, 2, 3, 4

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 1, 1, 4\n" +
           "Frame 1: -, 2, 2, 2, 2, 2\n" +
           "Frame 2: -, -, 3, 3, 3, 3\n"
        clock1.init(stream1.getPageRequestStream)
        clock1.showMemory() shouldBe expectedResult0
      }
    }
  }
}

