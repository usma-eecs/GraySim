package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.collection.mutable.Map

class ThreeProcesses_Test extends AnyFunSpec with Matchers {
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

  describe("Three Randomized Processes") {
    describe("knows about scheduling jobs in a FIFO fashion") {
      it("FIFO: randomTestOne") {
        Parameters.randomThreeOne
        var f = new fifoFixture
        f.fifo.showSchedule shouldBe "Trace: A, A, A, A, A, A, A, A, A, B, B, B, B, B, B, C, C, C"
      }
      it("FIFO: randomTestTwo") {
        Parameters.randomThreeTwo
        var f = new fifoFixture
        f.fifo.showSchedule shouldBe "Trace: A, A, A, A, B, B, B, B, B, B, B, C, C, C, C, C, C, C"
      }
    }
    describe("knows about scheduling jobs in a MLFQ fashion") {
      it("MLFQ: randomTestOne") {
        Parameters.randomThreeOne
        var f = new mlfqFixture
        f.mlfq.showSchedule shouldBe "Trace: A, A, A, B, B, B, C, C, C, A, A, A, A, B, B, B, A, A"
      }
      it("MLFQ: randomTestTwo") {
        Parameters.randomThreeTwo
        var f = new mlfqFixture
        f.mlfq.showSchedule shouldBe "Trace: A, A, A, A, B, C, B, B, C, C, B, B, B, B, C, C, C, C"
      }
    }
    describe("knows about scheduling jobs in a Round Robin fashion") {
      it("RR: randomTestOne with quantum 1") {
        Parameters.randomThreeOne
        var f = new rrFixture(1)
        f.rr.showSchedule shouldBe "Trace: A, A, B, A, B, A, B, C, A, B, C, A, B, C, A, B, A, A"
      }
      it("RR: randomTestTwo with quantum 1") {
        Parameters.randomThreeTwo
        var f = new rrFixture(1)
        f.rr.showSchedule shouldBe "Trace: A, A, A, A, B, C, B, C, B, C, B, C, B, C, B, C, B, C"
      }
      it("RR: randomTestOne with quantum 2") {
        Parameters.randomThreeOne
        var f = new rrFixture(2)
        f.rr.showSchedule shouldBe "Trace: A, A, B, B, A, A, B, B, C, C, A, A, B, B, C, A, A, A"
      }
      it("RR: randomTestTwo with quantum 2") {
        Parameters.randomThreeTwo
        var f = new rrFixture(2)
        f.rr.showSchedule shouldBe "Trace: A, A, A, A, B, B, C, C, B, B, C, C, B, B, C, C, B, C"
      }
    }
    describe("knows about scheduling jobs in a SJF fashion") {
      it("SJF: randomTestOne") {
        Parameters.randomThreeOne
        var f = new sjfFixture
        f.sjf.showSchedule shouldBe "Trace: A, A, A, A, A, A, A, A, A, C, C, C, B, B, B, B, B, B"
      }
      it("SJF: randomTestTwo") {
        Parameters.randomThreeTwo
        var f = new sjfFixture
        f.sjf.showSchedule shouldBe "Trace: A, A, A, A, B, B, B, B, B, B, B, C, C, C, C, C, C, C"
      }
    }
    describe("knows about scheduling jobs in a STCF fashion") {
      it("STCF: randomTestOne") {
        Parameters.randomThreeOne
        var f = new stcfFixture
        f.stcf.showSchedule shouldBe "Trace: A, A, B, B, B, B, B, B, C, C, C, A, A, A, A, A, A, A"
      }
      it("STCF: randomTestTwo") {
        Parameters.randomThreeTwo
        var f = new stcfFixture
        f.stcf.showSchedule shouldBe "Trace: A, A, A, A, B, B, B, B, B, B, B, C, C, C, C, C, C, C"
      }
    }
  }
}
