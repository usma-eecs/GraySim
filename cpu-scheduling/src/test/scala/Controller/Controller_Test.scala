package controller

import model._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class JobQueueFixture {
  var jobs = new JobQueue
  var processQ = new ProcessQueue
  jobs.init
  jobs.addProcess("C", 15, 2);
  jobs.addProcess("A", 5, 0);
  jobs.addProcess("D", 20, 3);
  jobs.addProcess("B", 10, 1);
}

class Controller_Test extends AnyFunSpec with Matchers {
  describe("The Controller") {
    describe("knows about queued jobs") {
      it("including their start time") {
        var jobs = new JobQueue
        jobs.addProcess("A", 5, 0);
        jobs.addProcess("B", 10, 1);
        jobs.addProcess("C", 15, 2);
        jobs.addProcess("D", 20, 3);

        val expectedResult: String =
          s"Job Queue: A(0,5), B(1,10), C(2,15), D(3,20)"
        jobs.show shouldBe expectedResult
      }
      it("can move a job off the job queue into the process queue") {
        var f = new JobQueueFixture
        var t = 0

        var result = f.jobs.dequeue(t)
        result match
            case Some(p) => f.processQ.addProcess(p)
            case None => true shouldBe false
        val expectedResult1: String = s"Process Queue: A(0,5)"

        t += 1
        result = f.jobs.dequeue(t)
        result match
            case Some(p) => f.processQ.addProcess(p)
            case None => true shouldBe false
        val expectedResult2: String = s"Process Queue: A(0,5), B(1,10)"
        f.processQ.show shouldBe expectedResult2

        t += 1
        result = f.jobs.dequeue(t)
        result match
            case Some(p) => f.processQ.addProcess(p)
            case None => true shouldBe false
        val expectedResult3: String = s"Process Queue: A(0,5), B(1,10), C(2,15)"
        f.processQ.show shouldBe expectedResult3

        t += 1
        result = f.jobs.dequeue(t)
        result match
            case Some(p) => f.processQ.addProcess(p)
            case None => true shouldBe false
        val expectedResult4: String = s"Process Queue: A(0,5), B(1,10), C(2,15), D(3,20)"
        f.processQ.show shouldBe expectedResult4

        t += 1
        f.jobs.dequeue(t) shouldBe None
      }
    }
  }
}
