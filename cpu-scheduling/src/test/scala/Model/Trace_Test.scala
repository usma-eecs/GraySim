package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class TraceFixture {
  var cpu = new Trace
  cpu.init

}

class Trace_Test extends AnyFunSpec with Matchers {
  describe("The Trace") {
    describe("knows about cpu schedules") {
      it("including what process is scheduled at each time") {
        var trace = new Trace
        var a = new Process("A", 5, 0)
        var b = new Process("B", 3, 0)
        trace.init
        var expectedResult = "Trace: A, B"
        trace.allocateCPU(a)
        trace.allocateCPU(b)
        trace.show shouldBe expectedResult
      }
      it("including complex scheduling") {
        var trace = new Trace
        var a = new Process("A", 5, 0)
        var b = new Process("B", 3, 0)
        trace.init
        var expectedResult = "Trace: A, B, B, A, A, A, B, A"
        trace.allocateCPU(a)
        trace.allocateCPU(b)
        trace.allocateCPU(b)
        trace.allocateCPU(a)
        trace.allocateCPU(a)
        trace.allocateCPU(a)
        trace.allocateCPU(b)
        trace.allocateCPU(a)
        trace.show shouldBe expectedResult
      }
    }
  }
}
