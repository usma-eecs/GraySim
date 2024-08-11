package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.collection.mutable.Map

class FiveProcesses_Test extends AnyFunSpec with Matchers {
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

  describe("Five Randomized Processes") {
    describe("knows about scheduling jobs in a FIFO fashion") {
      it("FIFO: randomTestOne") {
        Parameters.randomFiveOne
        var f = new fifoFixture
        f.fifo.showSchedule shouldBe "Trace: A, A, B, B, B, B, B, B, B, B, C, C, C, C, D, D, D, E"
      }
      it("FIFO: randomTestTwo") {
        Parameters.randomFiveTwo
        var f = new fifoFixture
        f.fifo.showSchedule shouldBe "Trace: A, A, A, A, A, A, B, B, B, C, C, D, D, D, D, D, D, D, E"
      }
    }
    describe("knows about scheduling jobs in a MLFQ fashion") {
      it("MLFQ: randomTestOne") {
        Parameters.randomFiveOne
        var f = new mlfqFixture
        f.mlfq.showSchedule shouldBe "Trace: A, B, A, B, B, C, C, C, D, E, D, D, B, B, B, B, C, B"
      }
      it("MLFQ: randomTestTwo") {
        Parameters.randomFiveTwo
        var f = new mlfqFixture
        f.mlfq.showSchedule shouldBe "Trace: A, B, C, D, E, A, A, B, B, C, D, D, A, A, A, D, D, D, D"
      }
    }
    describe("knows about scheduling jobs in a Round Robin fashion") {
      it("RR: randomTestOne with quantum 1") {
        Parameters.randomFiveOne
        var f = new rrFixture(1)
        f.rr.showSchedule shouldBe "Trace: A, B, A, B, B, C, B, C, D, B, C, E, D, B, C, D, B, B"
      }
      it("RR: randomTestTwo with quantum 1") {
        Parameters.randomFiveTwo
        var f = new rrFixture(1)
        f.rr.showSchedule shouldBe "Trace: A, B, A, C, B, D, A, E, C, B, D, A, D, A, D, A, D, D, D"
      }
      it("RR: randomTestOne with quantum 2") {
        Parameters.randomFiveOne
        var f = new rrFixture(2)
        f.rr.showSchedule shouldBe "Trace: A, A, B, B, B, B, C, C, B, B, D, D, C, C, E, B, B, D"
      }
      it("RR: randomTestTwo with quantum 2") {
        Parameters.randomFiveTwo
        var f = new rrFixture(2)
        f.rr.showSchedule shouldBe "Trace: A, A, B, B, C, C, A, A, D, D, E, B, A, A, D, D, D, D, D"
      }
    }
    describe("knows about scheduling jobs in a SJF fashion") {
      it("SJF: randomTestOne") {
        Parameters.randomFiveOne
        var f = new sjfFixture
        f.sjf.showSchedule shouldBe "Trace: A, A, B, B, B, B, B, B, B, B, E, D, D, D, C, C, C, C"
      }
      it("SJF: randomTestTwo") {
        Parameters.randomFiveTwo
        var f = new sjfFixture
        f.sjf.showSchedule shouldBe "Trace: A, A, A, A, A, A, E, C, C, B, B, B, D, D, D, D, D, D, D"
      }
    }
    describe("knows about scheduling jobs in a STCF fashion") {
      it("STCF: randomTestOne") {
        Parameters.randomFiveOne
        var f = new stcfFixture
        f.stcf.showSchedule shouldBe "Trace: A, A, B, B, B, C, C, C, C, E, D, D, D, B, B, B, B, B"
      }
      it("STCF: randomTestTwo") {
        Parameters.randomFiveTwo
        var f = new stcfFixture
        f.stcf.showSchedule shouldBe "Trace: A, B, B, B, E, C, C, A, A, A, A, A, D, D, D, D, D, D, D"
      }
    }
  }
}
