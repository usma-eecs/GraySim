package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.collection.mutable.Map

class STCF_Test extends AnyFunSpec with Matchers {
  class fixture {
    var stcf: Scheduler = new STCF
    stcf.init
    stcf.schedule
  }
  describe("The STCF scheduler") {
    describe("knows about scheduling jobs in a STCF fashion") {
      it("STCF: including when two jobs arrive at time 0") {
        Parameters.twoJobsArriveAtSameTime
        var s = new fixture
        s.stcf.showSchedule shouldBe "Trace: B, B, B, A, A, A, A, A"
      }
      it("STCF: including when one job starts before another") {
        Parameters.oneJobStartsBeforeAnother
        var s = new fixture
        s.stcf.showSchedule shouldBe "Trace: B, B, B, A, A, A, A, A"
      }
      it("STCF: including when there are overlaps between jobs") {
        Parameters.overlapBetweenJobs
        var s = new fixture
        s.stcf.showSchedule shouldBe "Trace: A, B, B, B, A, A, A, A, C, C, C, C"
      }
      it("STCF: the worksheet tests") {
        Parameters.givenTests
        var s = new fixture
        s.stcf.showSchedule shouldBe "Trace: A, C, A, A, A, B, B, E, E, E, E, E, B, B, B, B, B, B, D, D, D, D, D, D, D, D"
      }
      it("STCF: the lesson 8 ICE tests") {
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
        f.stcf.showSchedule shouldBe "Trace: A, A, A, A, A, B, D, B, B, E, E, E, B, B, B, B, B, C, C, C, C, C, C, C, C, C"
      }
    }
  }
}
