package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.collection.mutable.Map

class FourProcesses_Test extends AnyFunSpec with Matchers {
  def setFourProcessParameters1 =
    Parameters.resetProcessData
    Parameters.setProcessStartTime(0, 0)
    Parameters.setProcessServiceTime(0, 5)
    Parameters.setProcessStartTime(1, 2)
    Parameters.setProcessServiceTime(1, 2)
    Parameters.setProcessStartTime(2, 3)
    Parameters.setProcessServiceTime(2, 1)
    Parameters.setProcessStartTime(3, 8)
    Parameters.setProcessServiceTime(3, 2)
    Parameters.validateOptions()

  def setFourProcessParameters2 =
    Parameters.resetProcessData
    Parameters.setProcessStartTime(0, 0)
    Parameters.setProcessServiceTime(0, 5)
    Parameters.setProcessStartTime(1, 2)
    Parameters.setProcessServiceTime(1, 5)
    Parameters.setProcessStartTime(2, 3)
    Parameters.setProcessServiceTime(2, 5)
    Parameters.setProcessStartTime(3, 8)
    Parameters.setProcessServiceTime(3, 5)
    Parameters.validateOptions()


  class fifoFixture:
    var fifo: Scheduler = new FIFO
    fifo.init
    fifo.schedule
  class mlfqFixture:
    var mlfq: Scheduler = new MLFQ
    mlfq.init
    mlfq.schedule
  class rrFixture(quantum: Int):
    var rr: Scheduler = new RoundRobin
    rr.init
    Parameters.setQuantum(quantum)
    rr.schedule
  class sjfFixture:
    var sjf: Scheduler = new SJF
    sjf.init
    sjf.schedule
  class stcfFixture:
    var stcf: Scheduler = new STCF
    stcf.init
    stcf.schedule

  describe("Four Randomized Processes") {
    describe("knows about scheduling jobs in a FIFO fashion") {
      it("FIFO: randomTestOne") {
        setFourProcessParameters1
        var f = new fifoFixture
        f.fifo.showSchedule shouldBe "Trace: A, A, A, A, A, B, B, C, D, D"
      }
      it("FIFO: randomTestTwo") {
        setFourProcessParameters2
        var f = new fifoFixture
        f.fifo.showSchedule shouldBe "Trace: A, A, A, A, A, B, B, B, B, B, C, C, C, C, C, D, D, D, D, D"
      }
    }
    describe("knows about scheduling jobs in a MLFQ fashion") {
      it("MLFQ: randomTestOne") {
        setFourProcessParameters1
        var f = new mlfqFixture
        f.mlfq.showSchedule shouldBe "Trace: A, A, A, B, C, B, A, A, D, D"
      }
      it("MLFQ: randomTestTwo") {
        setFourProcessParameters2
        var f = new mlfqFixture
        f.mlfq.showSchedule shouldBe "Trace: A, A, A, B, C, B, B, C, C, D, D, D, A, A, B, B, C, C, D, D"
      }
    }
    describe("knows about scheduling jobs in a Round Robin fashion") {
      it("RR: randomTestOne with quantum 1") {
        setFourProcessParameters1
        var f = new rrFixture(1)
        f.rr.showSchedule shouldBe "Trace: A, A, B, A, C, B, A, A, D, D"
      }
      it("RR: randomTestTwo with quantum 1") {
        setFourProcessParameters2
        var f = new rrFixture(1)
        f.rr.showSchedule shouldBe "Trace: A, A, B, A, C, B, A, C, B, A, D, C, B, D, C, B, D, C, D, D"
      }
      it("RR: randomTestOne with quantum 2") {
        setFourProcessParameters1
        var f = new rrFixture(2)
        f.rr.showSchedule shouldBe "Trace: A, A, B, B, A, A, C, A, D, D"
      }
      it("RR: randomTestTwo with quantum 2") {
        setFourProcessParameters2
        var f = new rrFixture(2)
        f.rr.showSchedule shouldBe "Trace: A, A, B, B, A, A, C, C, B, B, A, D, D, C, C, B, D, D, C, D"
      }
    }
    describe("knows about scheduling jobs in a SJF fashion") {
      it("SJF: randomTestOne") {
        setFourProcessParameters1
        var f = new sjfFixture
        f.sjf.showSchedule shouldBe "Trace: A, A, A, A, A, C, B, B, D, D"
      }
      it("SJF: randomTestTwo") {
        setFourProcessParameters2
        var f = new sjfFixture
        f.sjf.showSchedule shouldBe "Trace: A, A, A, A, A, B, B, B, B, B, C, C, C, C, C, D, D, D, D, D"
      }
    }
    describe("knows about scheduling jobs in a STCF fashion") {
      it("STCF: randomTestOne") {
        setFourProcessParameters1
        var f = new stcfFixture
        f.stcf.showSchedule shouldBe "Trace: A, A, B, B, C, A, A, A, D, D"
      }
      it("STCF: randomTestTwo") {
        setFourProcessParameters2
        var f = new stcfFixture
        f.stcf.showSchedule shouldBe "Trace: A, A, A, A, A, B, B, B, B, B, C, C, C, C, C, D, D, D, D, D"
      }
    }
  }
}
