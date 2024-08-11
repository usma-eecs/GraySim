package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class FIFO_Test extends AnyFunSpec with Matchers {

  describe("The FIFO Page Replacement Algorithm") {
    describe("knows about page replacement in a FIFO fashion") {
      it("shows basic FIFO with 4 pages"){
        val fifo = new FIFO(3)

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
        fifo.init(stream.getPageRequestStream)
        fifo.showMemory() shouldBe expectedResult
      }
      it("shows more complex FIFO: entire CS481 ICE"){
        val fifo1 = new FIFO(3)

        // Page Request Stream (2, 3, 1, 3, 4, 3, 5, 4, 3, 2, 1, 3, 6, 3)
        val stream1 = new PageRequestStream(0)

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 2, 2, 2, 2, 4, 4, 4, 4, 4, 2, 2, 2, 2, 3\n" +
           "Frame 1: -, 3, 3, 3, 3, 3, 5, 5, 5, 5, 1, 1, 1, 1\n" +
           "Frame 2: -, -, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 6, 6\n"

        fifo1.init(stream1.getPageRequestStream)
        fifo1.showMemory() shouldBe expectedResult0
      }
      it("can do a random page request stream correctly"){
        val fifo1 = new FIFO(3)

        // Page Request Stream
        val stream1 = new PageRequestStream(5)

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 4, 4, 4\n" +
           "Frame 1: -, 5, 5, 5, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3\n" +
           "Frame 2: -, -, 3, 3, 3, 3, 5, 5, 5, 5, 2, 2, 2, 2\n"

        fifo1.init(stream1.getPageRequestStream)
        fifo1.showMemory() shouldBe expectedResult0
      }
      it("can do a page request stream that differs for all policies") {
        val fifo1 = new FIFO(3)

        // Page Request Stream
        val stream1 = new PageRequestStream(2) // 1, 2, 3, 2, 1, 4, 2, 5, 6

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 1, 1, 4, 4, 4, 4\n" +
           "Frame 1: -, 2, 2, 2, 2, 2, 2, 5, 5\n" +
           "Frame 2: -, -, 3, 3, 3, 3, 3, 3, 6\n"
        fifo1.init(stream1.getPageRequestStream)
        fifo1.showMemory() shouldBe expectedResult0
      }
      it("can do a page request stream that is the same for all policies") {
        val fifo1 = new FIFO(3)

        // Page Request Stream
        val stream1 = new PageRequestStream(3) // 1, 2, 3, 2, 3, 4
        stream1.clear
        stream1.addPageRequest(1);
        stream1.addPageRequest(2);
        stream1.addPageRequest(3);
        stream1.addPageRequest(2);
        stream1.addPageRequest(3);
        stream1.addPageRequest(4);
        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 1, 1, 4\n" +
           "Frame 1: -, 2, 2, 2, 2, 2\n" +
           "Frame 2: -, -, 3, 3, 3, 3\n"
        fifo1.init(stream1.getPageRequestStream)
        fifo1.showMemory() shouldBe expectedResult0
      }
    }
  }
}
