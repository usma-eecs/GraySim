package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class JobQueueFixture {
  var jobs = new JobQueue
  jobs.init
  jobs.addProcess("A", 5, 0);
  jobs.addProcess("B", 10, 1);
  jobs.addProcess("C", 15, 2);
  jobs.addProcess("D", 20, 3);
}

class JobQueue_Test extends AnyFunSpec with Matchers {
  describe("The JobQueue") {
    describe("knows about queued jobs") {
      it("including their start time") {
        var jobs = new JobQueue
        jobs.init
        jobs.addProcess("A", 5, 0)
        val expectedResult: String =
          s"Job Queue: A(0,5)"
        jobs.show shouldBe expectedResult
      }
      it("including their priority") {
        var jobs = new JobQueue
        jobs.init
        jobs.addProcess("B", 10, 1)
        jobs.addProcess("A", 5, 0)
        val expectedResult0: String =
        s"Job Queue: A(0,5), B(1,10)"
        jobs.show shouldBe expectedResult0

        val expectedResult1a: String =
          s"A(0,5)"
        val expectedResult1b: String =
            s"Job Queue: B(1,10)"
        jobs.dequeue().toString shouldBe expectedResult1a
        jobs.show shouldBe expectedResult1b

        val expectedResult2a: String =
          s"B(1,10)"
        val expectedResult2b: String =
          s"Job Queue: "
        jobs.dequeue().toString shouldBe expectedResult2a
        jobs.show shouldBe expectedResult2b
      }
      it("knows when jobs start") {
        var f = new JobQueueFixture
        val expectedResult1: String =
          s"Job Queue: A(0,5), B(1,10), C(2,15), D(3,20)"
        f.jobs.show shouldBe expectedResult1
        f.jobs.dequeue()
        f.jobs.dequeue()
        val expectedResult2: String = s"Job Queue: C(2,15), D(3,20)"
        f.jobs.show shouldBe expectedResult2
        f.jobs.dequeue(0) shouldBe None
        f.jobs.dequeue(1) === None
        var j = f.jobs.dequeue(2)
        j match
          case Some(p: Process) => p.toString() shouldBe "C(2,15)"
          case None => true shouldBe false
        var k = f.jobs.dequeue(3)
        k match
          case Some(p: Process) => p.toString() shouldBe "D(3,20)"
          case None => true shouldBe false

      }
    }
  }
}
