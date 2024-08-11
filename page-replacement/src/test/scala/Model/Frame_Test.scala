package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

// is this necessary?
class FrameFixture {
    val sampleframe = new Frame

    sampleframe.init

    sampleframe.addPagetoFrame(1);
    sampleframe.addPagetoFrame(2);
    sampleframe.addPagetoFrame(3);

    def frameBuilder(f0: Frame, f1: Frame, f2: Frame): String =
      val result =
        f0.show + "\n" + f1.show + "\n" + f2.show + "\n"
      result
}

class Frame_Test extends AnyFunSpec with Matchers {
  describe("The Frame") {
    describe("knows about the frames") {
      it("shows one frame") {
        val frame = new Frame
        frame.frameNum = 0
        frame.addPagetoFrame(1);

        val expectedResult: String =
          s"Frame 0: 1"
        frame.show shouldBe expectedResult
      }
      it("can add muliple pages and have different frame numbers") {
        val frame = new Frame
        frame.frameNum = 1
        frame.init
        frame.addPagetoFrame(1);
        frame.addPagetoFrame(2);
        frame.addPagetoFrame(3);

        val expectedResult0: String =
        s"Frame 1: 1, 2, 3"
        frame.show shouldBe expectedResult0
      }
      it("can show multiple frames in order"){
        val frame0 = new Frame
        val frame1 = new Frame
        val frame2 = new Frame
        frame0.frameNum = 0
        frame1.frameNum = 1
        frame2.frameNum = 2
        frame0.init
        frame1.init
        frame2.init
        frame0.addPagetoFrame(1);
        frame1.addPagetoFrame(2);
        frame2.addPagetoFrame(3);
        val sampleframe = new FrameFixture

        val expectedResult0: String =
          // s"Frame 0: 1, 1, 1\n" +
          //  "Frame 1: -, 2, 2\n" +
          //  "Frame 2: -, -, 3"

          s"Frame 0: 1\n" +
           "Frame 1: 2\n" +
           "Frame 2: 3\n"

          //current issue is the zeros are supposed to represent blanks. try option int instead.
          // s"Frame 0: 1, 1, 1\n" +
          //  "Frame 1: 0, 2, 2\n" +
          //  "Frame 2: 0, 0, 3\n"
        sampleframe.frameBuilder(frame0, frame1, frame2) shouldBe expectedResult0
      }
    }
  }
}