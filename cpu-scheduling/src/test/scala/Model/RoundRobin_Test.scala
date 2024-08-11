package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.collection.mutable.Map

class RoundRobin_Test extends AnyFunSpec with Matchers {
  class fixture(quantum: Int):
    var rr: Scheduler = new RoundRobin
    rr.init
    Parameters.setQuantum(quantum)
    rr.schedule
    //Parameters.setQuantum(quantum)
  describe("The RoundRobin scheduler") {
    describe("knows about scheduling jobs in a RoundRobin fashion") {

      it("RR: including when two jobs arrive at time 0") {
        Parameters.twoJobsArriveAtSameTime
        var r = new fixture(1)
        r.rr.showSchedule shouldBe "Trace: A, B, A, B, A, B, A, A"
      }
      it("RR: including when one job starts before another") {
        Parameters.oneJobStartsBeforeAnother
        var r = new fixture(1)
        r.rr.showSchedule shouldBe "Trace: B, A, B, A, B, A, A, A"
      }
      // it("RR: including when there are delays before and between jobs") {
      //   var r = new fixture(1)
      //   Parameters.delaysBeforeAndBetweenJobs
      //   r.rr.showSchedule shouldBe "Trace: Idle, B, B, B, Idle, Idle, Idle, A, A, A, A, A"
      // }
      it("RR: including when there are overlaps between jobs") {
        Parameters.overlapBetweenJobs
        var r = new fixture(1)
        r.rr.showSchedule shouldBe "Trace: A, B, A, C, B, A, C, B, A, C, A, C"
      }
      it("RR: including when the quantum is 2") {
        Parameters.overlapBetweenJobs
        var r = new fixture(2)
        r.rr.showSchedule shouldBe "Trace: A, A, B, B, C, C, A, A, B, C, C, A"
      }
      it("RR: including when the quantum is 3") {
        Parameters.overlapBetweenJobs
        var r = new fixture(3)
        r.rr.showSchedule shouldBe "Trace: A, A, A, B, B, B, C, C, C, A, A, C"
      }
      it("RR: including when the quantum is 4") {
        Parameters.overlapBetweenJobs
        var r = new fixture(4)
        r.rr.showSchedule shouldBe "Trace: A, A, A, A, B, B, B, C, C, C, C, A"
      }
      it("RR: the worksheet tests") {
        Parameters.givenTests
        var r = new fixture(1)
        r.rr.showSchedule shouldBe "Trace: A, B, C, A, B, D, A, B, D, E, A, B, D, E, B, D, E, B, D, E, B, D, E, B, D, D"// D, A, B, B, E, D, B, E, D, E, D, E, D, D, D"
      }
      it("RR: the lesson 7 tests") {
        Parameters.resetProcessData
        Parameters.setProcessStartTime(0, 0)
        Parameters.setProcessServiceTime(0, 3)
        Parameters.setProcessStartTime(1, 2)
        Parameters.setProcessServiceTime(1, 6)
        Parameters.setProcessStartTime(2, 4)
        Parameters.setProcessServiceTime(2, 4)
        Parameters.setProcessStartTime(3, 6)
        Parameters.setProcessServiceTime(3, 5)
        Parameters.setProcessStartTime(4, 8)
        Parameters.setProcessServiceTime(4, 2)
        Parameters.validateOptions()
        var f = new fixture(1)
        f.rr.showSchedule shouldBe "Trace: A, A, B, A, B, C, B, D, C, B, E, D, C, B, E, D, C, B, D, D"
      }
      it("RR: the lesson 8 ICE tests") {
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
        var f = new fixture(1)
        f.rr.showSchedule shouldBe "Trace: A, B, A, B, C, A, B, C, D, A, B, C, E, A, B, C, E, B, C, E, B, C, B, C, C, C"
        var f2 = new fixture(4)
        f2.rr.showSchedule shouldBe "Trace: A, A, A, A, B, B, B, B, C, C, C, C, A, D, B, B, B, B, E, E, E, C, C, C, C, C"
      }
    }
  }
}
