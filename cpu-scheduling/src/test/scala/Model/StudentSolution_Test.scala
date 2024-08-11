package model

import scala.collection.mutable.Map

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class StudentSolution_Test extends AnyFunSpec with Matchers {
  describe("The student solution") {
    describe("knows about how to manage student solutions") {
      it("including how to track when they have scheduled various processes") {
        Parameters.resetProcessData
        Parameters.setProcessStartTime(0, 0)
        Parameters.setProcessServiceTime(0, 3)
        Parameters.setProcessStartTime(1, 1)
        Parameters.setProcessServiceTime(1, 2)
        Parameters.validateOptions()
        var plan = new StudentSolution

        plan.schedule("A", 2)
        plan.schedule("B", 0)
        plan.schedule("B", 4)
        val expectedResult = "A: - - A - - \nB: B - - - B \n"
        plan.show shouldBe expectedResult
      }
      it(
        "including how to determine whether a process is scheduled in a timeslot"
      ) {
        Parameters.resetProcessData
        Parameters.setProcessStartTime(0, 0)
        Parameters.setProcessServiceTime(0, 3)
        Parameters.setProcessStartTime(1, 1)
        Parameters.setProcessServiceTime(1, 2)
        Parameters.validateOptions()
        var plan = new StudentSolution

        plan.schedule("A", 2)
        plan.schedule("B", 0)
        plan.schedule("B", 4)
        plan.isScheduled(0, 2) shouldBe true
        plan.isScheduled(0, 0) shouldBe false
        plan.isScheduled(1, 4) shouldBe true
        plan.isScheduled(1, 2) shouldBe false
      }
      it("including how to calculate the lengths during which the process was scheduled (baby steps)") {
        Parameters.resetProcessData
        Parameters.setProcessStartTime(0, 0)
        Parameters.setProcessServiceTime(0, 3)
        Parameters.validateOptions()
        var plan = new StudentSolution
        plan.schedule("A", 0)
        plan.schedule("A", 1)
        plan.schedule("A", 2)
        plan.processScheduledLengths("A").toString() shouldBe "ArrayBuffer(3)"
      }
      it("including how to calculate the lengths during which the process was scheduled") {
        Parameters.resetProcessData
        Parameters.setProcessStartTime(0, 0)
        Parameters.setProcessServiceTime(0, 4)
        Parameters.setProcessStartTime(1, 0)
        Parameters.setProcessServiceTime(1, 7)
        Parameters.validateOptions()
        var plan = new StudentSolution
        plan.schedule("A", 0)
        plan.schedule("B", 1)
        plan.schedule("A", 2)
        plan.schedule("A", 3)
        plan.schedule("B", 4)
        plan.schedule("B", 5)
        plan.schedule("A", 6)
        plan.schedule("B", 7)
        plan.schedule("B", 8)
        plan.schedule("B", 9)
        plan.schedule("B", 10)
        plan.processScheduledLengths("A").toString() shouldBe "ArrayBuffer(1, 2, 1)"
        plan.processScheduledLengths("B").toString() shouldBe "ArrayBuffer(1, 2, 4)"
      }
    }
  }
}
