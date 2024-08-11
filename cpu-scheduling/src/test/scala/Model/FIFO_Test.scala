package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.collection.mutable.Map

class FIFO_Test extends AnyFunSpec with Matchers {
  class fixture {
    var fifo: Scheduler = new FIFO
    fifo.init
    fifo.schedule
  }
  describe("The FIFO scheduler") {
    describe("knows about scheduling jobs in a FIFO fashion") {
      it("FIFO: including when two jobs arrive at time 0") {
        Parameters.twoJobsArriveAtSameTime
        var f = new fixture
        f.fifo.showSchedule shouldBe "Trace: A, A, A, A, A, B, B, B"
      }
      it("FIFO: including when one job starts before another") {
        Parameters.oneJobStartsBeforeAnother
        var f = new fixture
        f.fifo.showSchedule shouldBe "Trace: B, B, B, A, A, A, A, A"
      }
      // it("FIFO: including when there are delays before and between jobs") {
      //   Parameters.delayBeforeAndBetweenJobs
      //   var f = new fixture
      //   f.fifo.showSchedule shouldBe "Trace: Idle, B, B, B, Idle, Idle, Idle, A, A, A, A, A"
      // }
      it("FIFO: including when there are overlaps between jobs") {
        Parameters.overlapBetweenJobs
        var f = new fixture
        f.fifo.showSchedule shouldBe "Trace: A, A, A, A, A, B, B, B, C, C, C, C"
      }
      it("FIFO: the worksheet tests") {
        Parameters.givenTests
        var f = new fixture
        f.fifo.showSchedule shouldBe "Trace: A, A, A, A, B, B, B, B, B, B, B, B, C, D, D, D, D, D, D, D, D, E, E, E, E, E"
      }
      it("FIFO: the lesson 7 tests") {
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
        var f = new fixture
        f.fifo.showSchedule shouldBe "Trace: A, A, A, B, B, B, B, B, B, C, C, C, C, D, D, D, D, D, E, E"
      }
      it("FIFO: the lesson 8 ICE tests") {
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
        f.fifo.showSchedule shouldBe "Trace: A, A, A, A, A, B, B, B, B, B, B, B, B, C, C, C, C, C, C, C, C, C, D, E, E, E"
      }
    }
  }
}
