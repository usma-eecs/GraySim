package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class ProcessQueueFixture {
  var processes = new ProcessQueue
  processes.init
  var a = new Process("A", 5, 0);
  var b = new Process("B", 10, 1);
  var c = new Process("C", 15, 2);
  var d = new Process("D", 20, 3);

  processes.addProcess(d);
  processes.addProcess(a);
  processes.addProcess(b);
  processes.addProcess(c);
}

class ProcessQueue_Test extends AnyFunSpec with Matchers {
  describe("The ProcessQueue") {
    describe("knows about queued processes") {
      it("PQ: including their start time") {
        var processes = new ProcessQueue
        var a = new Process("A", 5, 0)
        processes.init
        processes.addProcess(a)
        val expectedResult: String =
          s"Process Queue: A(0,5)"
        processes.show shouldBe expectedResult
      }
      it("PQ: including their proper order them in the queue") {
        var processes = new ProcessQueue
        var a = new Process("A", 5, 0);
        var b = new Process("B", 10, 1);
        processes.init
        processes.addProcess(b)
        processes.addProcess(a)
        val first = processes.removeProcess()
        first.getName shouldBe a.getName
        val second = processes.removeProcess()
        second.getName shouldBe b.getName
      }
      it("PQ: knows when jobs start") {
        var f = new ProcessQueueFixture
        val expectedResult1: String =
          s"Process Queue: D(3,20), A(0,5), B(1,10), C(2,15)"
        val first = f.processes.removeProcess()
        val second = f.processes.removeProcess()
        val third = f.processes.removeProcess()
        val fourth = f.processes.removeProcess()
        first.getName shouldBe "A"
        second.getName shouldBe "B"
        third.getName shouldBe "C"
        fourth.getName shouldBe "D"

      }
    }
  }
}
