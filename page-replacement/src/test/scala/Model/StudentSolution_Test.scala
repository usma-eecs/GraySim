package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class StudentSolution_Test extends AnyFunSpec with Matchers {
  describe("The StudentSolution") {
    describe("knows about the student's solution") {
      it("can initialize itself") {
        val answer = new StudentSolution(3,15)
        val expectedResult: String =
          s"Frame 0: - - - - - - - - - - - - - - - \n" +
           "Frame 1: - - - - - - - - - - - - - - - \n" +
           "Frame 2: - - - - - - - - - - - - - - - \n"
        answer.init
        answer.show shouldBe expectedResult
      }
      it("can keep track of student placements") {
        val answer = new StudentSolution(4,15)

        val expectedResult: String =
          s"Frame 0: A A A A - - - - - - - - - - - \n" +
           "Frame 1: - B B B - - - - - - - - - - - \n" +
           "Frame 2: - - C C - - - - - - - - - - - \n" +
           "Frame 3: - - - D - - - - - - - - - - - \n"


        answer.init
        answer.pageIn(0, 0, "A")
        answer.pageIn(1, 1, "B")
        answer.pageIn(2, 2, "C")
        answer.pageIn(3, 3, "D")
        answer.show shouldBe expectedResult
      }
    }
    describe("knows how to provide the student with accurate feedback") {
      it("can detect duplicates") {
        val answer = new StudentSolution(3, 5)

        val expectedResult: String =
          s"Frame 0: A A A - - \n" +
           "Frame 1: - B B - - \n" +
           "Frame 2: - - C - - \n"

        answer.init
        answer.pageIn(0, 0, "A")
        answer.pageIn(1, 1, "B")
        answer.pageIn(2, 2, "C")
        answer.show shouldBe expectedResult
        answer.hasDuplicate shouldBe false
        answer.pageIn(1, 3, "A")
        val expectedResult1: String =
          s"Frame 0: A A A A - \n" +
           "Frame 1: - B B A - \n" +
           "Frame 2: - - C C - \n"
        answer.show shouldBe expectedResult1
        answer.hasDuplicate shouldBe true
      }
      it("can detect complete solutions") {
        val answer = new StudentSolution(3, 5)

        val expectedResult: String =
          s"Frame 0: A A A - - \n" +
           "Frame 1: - B B - - \n" +
           "Frame 2: - - C - - \n"

        answer.init
        answer.pageIn(0, 0, "A")
        answer.pageIn(1, 1, "B")
        answer.pageIn(2, 2, "C")
        answer.show shouldBe expectedResult
        answer.complete shouldBe false
        answer.pageIn(1, 3, "A")
        val expectedResult1: String =
          s"Frame 0: A A A A - \n" +
           "Frame 1: - B B A - \n" +
           "Frame 2: - - C C - \n"
        answer.show shouldBe expectedResult1
        answer.complete shouldBe false
        answer.pageIn(2, 4, "D")
        val expectedResult2: String =
          s"Frame 0: A A A A A \n" +
           "Frame 1: - B B A A \n" +
           "Frame 2: - - C C D \n"
        answer.show shouldBe expectedResult2
        answer.complete shouldBe true
        answer.pageIn(0, 0, "-")
        val expectedResult3: String =
          s"Frame 0: - A A A A \n" +
           "Frame 1: - B B A A \n" +
           "Frame 2: - - C C D \n"
        answer.show shouldBe expectedResult3
        answer.complete shouldBe false
      }
    }
    describe("knows about the history of a student's solution") {
      it("allows the student to undo their last action") {
        val answer = new StudentSolution(3, 5)
        val expectedResultInitial: String =
          s"Frame 0: - - - - - \n" +
           "Frame 1: - - - - - \n" +
           "Frame 2: - - - - - \n"
        val expectedResultA: String =
          s"Frame 0: A - - - - \n" +
           "Frame 1: - - - - - \n" +
           "Frame 2: - - - - - \n"
        val expectedResultB: String =
          s"Frame 0: A A - - - \n" +
           "Frame 1: - B - - - \n" +
           "Frame 2: - - - - - \n"
        val expectedResultC: String =
          s"Frame 0: A A A - - \n" +
           "Frame 1: - B B - - \n" +
           "Frame 2: - - C - - \n"
        val expectedResultD: String =
          s"Frame 0: A A A A - \n" +
           "Frame 1: - B B B - \n" +
           "Frame 2: - - C D - \n"
        val expectedResultE: String =
          s"Frame 0: A A A A - \n" +
           "Frame 1: - B B E - \n" +
           "Frame 2: - - C C - \n"
        answer.init
        answer.pageIn(0, 0, "A")
        answer.pageIn(1, 1, "B")
        answer.pageIn(2, 2, "C")
        answer.show shouldBe expectedResultC
        answer.pageIn(2, 3, "D")
        answer.show shouldBe expectedResultD
        answer.pop shouldBe "undone"
        answer.show shouldBe expectedResultC
        answer.pageIn(1, 3, "E")
        answer.show shouldBe expectedResultE
        answer.pop shouldBe "undone"
        answer.show shouldBe expectedResultC
        answer.pop shouldBe "undone"
        answer.show shouldBe expectedResultB
        answer.pop shouldBe "undone"
        answer.show shouldBe expectedResultA
        answer.pop shouldBe "undone"
        answer.show shouldBe expectedResultInitial
        answer.pop shouldBe "Nothing to be undone"
      }
    }
}
}