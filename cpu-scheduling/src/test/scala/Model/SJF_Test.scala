package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.collection.mutable.Map

class SJF_Test extends AnyFunSpec with Matchers {
  class fixture:
    var sjf: Scheduler = new SJF
    sjf.init
    sjf.schedule

  describe("The SJF scheduler") {
    describe("knows about scheduling jobs in a SJF fashion") {
      it("SJF: including when two jobs arrive at time 0") {
        // Parameters.setPolicy(Parameters.Policies.SJF)
        Parameters.twoJobsArriveAtSameTime
        var f = new fixture
        f.sjf.showSchedule shouldBe "Trace: B, B, B, A, A, A, A, A"
      }
      it("SJF: including when one job starts before another") {
        // Parameters.setPolicy(Parameters.Policies.SJF)
        Parameters.oneJobStartsBeforeAnother
        var f = new fixture
        f.sjf.showSchedule shouldBe "Trace: B, B, B, A, A, A, A, A"
      }
      // it("SJF: including when there are delays before and between jobs") {
      //   Parameters.delaysBeforeAndBetweenJobs
      //   var f = new fixture
      //   f.sjf.showSchedule shouldBe "Trace: Idle, B, B, B, Idle, Idle, Idle, A, A, A, A, A"
      // }
      it("SJF: including when there are overlaps between jobs") {
        Parameters.overlapBetweenJobs
        var f = new fixture
        f.sjf.showSchedule shouldBe "Trace: A, A, A, A, A, B, B, B, C, C, C, C"
      }
      it("SJF: the worksheet tests") {
        Parameters.givenTests
        var f = new fixture
        f.sjf.showSchedule shouldBe "Trace: A, A, A, A, C, B, B, B, B, B, B, B, B, E, E, E, E, E, D, D, D, D, D, D, D, D"
      }
      it("SJF: the lesson 7 tests") {
        Parameters.resetProcessData
        Parameters.setProcessStartTime(0, 0)
        Parameters.setProcessServiceTime(0, 11)
        Parameters.setProcessStartTime(1, 0)
        Parameters.setProcessServiceTime(1, 8)
        Parameters.setProcessStartTime(2, 1)
        Parameters.setProcessServiceTime(2, 2)
        Parameters.setProcessStartTime(3, 3)
        Parameters.setProcessServiceTime(3, 4)
        Parameters.setProcessStartTime(4, 7)
        Parameters.setProcessServiceTime(4, 1)
        Parameters.validateOptions()
        var f = new fixture
        f.sjf.showSchedule shouldBe "Trace: B, B, B, B, B, B, B, B, E, C, C, D, D, D, D, A, A, A, A, A, A, A, A, A, A, A"
      }
      it("SJF: the lesson 8 ICE tests") {
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
        f.sjf.showSchedule shouldBe "Trace: A, A, A, A, A, B, B, B, B, B, B, B, B, D, E, E, E, C, C, C, C, C, C, C, C, C"
      }
    }
  }
}
