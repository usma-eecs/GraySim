package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class LRU_Test extends AnyFunSpec with Matchers {

  describe("The LRU Page Replacement Algorithm") {
    describe("knows about page replacement in a LRU fashion") {
      it("shows basic LRU with 4 pages"){
        val lru = new LRU(3)

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

        lru.init(stream.getPageRequestStream)
        lru.showMemory() shouldBe expectedResult
      }
      it("shows more complex LRU: entire CS481 ICE"){
        val lru1 = new LRU(3)

        // Page Request Stream (2, 3, 1, 3, 4, 3, 5, 4, 3, 2, 1, 3, 6, 3)
        val stream1 = new PageRequestStream(0)

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1\n" +
           "Frame 1: -, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3\n" +
           "Frame 2: -, -, 1, 1, 1, 1, 5, 5, 5, 2, 2, 2, 6, 6\n"

        lru1.init(stream1.getPageRequestStream)
        lru1.showMemory() shouldBe expectedResult0
      }
      it("shows more complex LRU: random test I made up"){
        val lru1 = new LRU(3)

        // Page Request Stream (1, 5, 3, 2, 4, 3, 5, 2, 1, 3, 2, 4, 3, 3)
        val stream1 = new PageRequestStream(5)

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 2, 2, 2, 5, 5, 5, 3, 3, 3, 3, 3\n" +
           "Frame 1: -, 5, 5, 5, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2\n" +
           "Frame 2: -, -, 3, 3, 3, 3, 3, 3, 1, 1, 1, 4, 4, 4\n"

        lru1.init(stream1.getPageRequestStream)
        lru1.showMemory() shouldBe expectedResult0
      }
      it("can do a page request stream that differs for all policies") {
        val lru1 = new LRU(3)

        // Page Request Stream
        val stream1 = new PageRequestStream(2) // 1, 2, 3, 2, 1, 4, 2, 5, 6

        val expectedResult0: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 1, 1, 1, 1, 5, 5\n" +
           "Frame 1: -, 2, 2, 2, 2, 2, 2, 2, 2\n" +
           "Frame 2: -, -, 3, 3, 3, 4, 4, 4, 6\n"
        lru1.init(stream1.getPageRequestStream)
        lru1.showMemory() shouldBe expectedResult0
      }
      it("can do a page request stream that is the same for all policies") {
        val lru1 = new LRU(3)

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
        lru1.init(stream1.getPageRequestStream)
        lru1.showMemory() shouldBe expectedResult0
      }
    }
  }
}

