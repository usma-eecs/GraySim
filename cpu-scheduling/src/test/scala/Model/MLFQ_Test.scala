package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer

class MLFQ_Test extends AnyFunSpec with Matchers {
  class fixture {
    var mlfq: Scheduler = new MLFQ
    mlfq.init
    mlfq.schedule
  }
  describe("The MLFQ scheduler") {
    describe("knows about scheduling jobs in a MLFQ fashion") {
      it("MLFQ: including when two jobs arrive at time 0") {
        Parameters.twoJobsArriveAtSameTime
        var m = new fixture
        m.mlfq.showSchedule shouldBe "Trace: A, B, A, A, B, B, A, A"
      }
      it("MLFQ: including when one job starts before another") {
        Parameters.oneJobStartsBeforeAnother
        var m = new fixture
        m.mlfq.showSchedule shouldBe "Trace: B, A, B, B, A, A, A, A"
      }
      // it("FCFS: including when there are delays before and between jobs") {
      //   Parameters.delaysBeforeAndBetweenJobs
      //   var m = new fixture
      //   m.mlfq.showSchedule shouldBe "Trace:"
      // }
      it("MLFQ: including when there are overlaps between jobs") {
        Parameters.overlapBetweenJobs
        var m = new fixture
        // if 5 > 2 then println("walker is cool")

        m.mlfq.showSchedule shouldBe "Trace: A, B, C, A, A, B, B, C, C, A, A, C" // A, A, A, B, C, B, B, C, C, A, A, C"
      }
      it("MLFQ: the worksheet tests") {
        Parameters.givenTests
        var m = new fixture
        m.mlfq.showSchedule shouldBe "Trace: A, B, C, A, A, D, B, B, E, D, D, E, E, A, B, B, B, B, D, D, D, D, E, E, B, D"
      }
      it("MLFQ: can check that schedules are in increasing orders of magnitude") {
        var m = new MLFQ
        val good_a1 = ArrayBuffer(1,2,4,8)
        val good_a2 = ArrayBuffer(3,12)
        val good_a3 = ArrayBuffer(15)
        val bad_a1 = ArrayBuffer(2,4,8)
        val bad_a2 = ArrayBuffer(8,4,2,1)
        m.increasingMagnitudes(good_a1, 15) shouldBe true
        m.increasingMagnitudes(good_a2, 15) shouldBe true
        m.increasingMagnitudes(good_a3, 15) shouldBe true
        m.increasingMagnitudes(bad_a1, 14) shouldBe false
        m.increasingMagnitudes(bad_a2, 15) shouldBe false
      }
      it("MLFQ: the lesson 8 ICE tests") {
        Parameters.resetProcessData
        Parameters.setProcessStartTime(0, 0)
        Parameters.setProcessServiceTime(0, 5)
        Parameters.setProcessStartTime(1, 0)
        Parameters.setProcessServiceTime(1, 8)
        Parameters.setProcessStartTime(2, 3)
        Parameters.setProcessServiceTime(2, 9)
        Parameters.setProcessStartTime(3, 6)
        Parameters.setProcessServiceTime(3, 1)
        Parameters.setProcessStartTime(4, 9)
        Parameters.setProcessServiceTime(4, 3)
        Parameters.validateOptions()
        var f = new fixture
        f.mlfq.showSchedule shouldBe "Trace: A, B, A, A, C, B, B, D, C, C, E, E, E, A, A, B, B, B, B, C, C, C, C, B, C, C"
      }
      it("MLFQ: can test that the student solution schedules in increasing powers of 2 until completed") {
        Parameters.resetProcessData
        var m = new fixture
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

        // Note: provideFeedback is only called when the solution is incorrect. The solution above is correct
        m.mlfq.provideFeedback(plan) shouldBe "Your solution is incorrect. Unfortunately, we have no specific feedback for you. \n\nPlease send a screenshot of your solution to your instructor so that we can improve this tool."

        plan = new StudentSolution
        plan.schedule("A", 0)
        plan.schedule("A", 1)
        plan.schedule("B", 2)
        plan.schedule("A", 3)
        plan.schedule("B", 4)
        plan.schedule("B", 5)
        plan.schedule("A", 6)
        plan.schedule("B", 7)
        plan.schedule("B", 8)
        plan.schedule("B", 9)
        plan.schedule("B", 10)
        m.mlfq.provideFeedback(plan) shouldBe "Your solution is incorrect. Consider the following observations:\n  - At least one process was not scheduled in lengths of increasing powers of 2.\n"
      }
    }
  }
}
