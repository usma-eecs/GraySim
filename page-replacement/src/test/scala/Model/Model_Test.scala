package model

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._

class ModelFixture {
        val model = new Model(3)
        model.init

        // Create the student solutions
        for policy <- Policy.values do
          model.pageIn(policy, 0, "1")
          model.pageIn(policy, 1, "2")
          model.pageIn(policy, 2, "3")
          model.pageIn(policy, 1, "2")
          model.pageIn(policy, 2, "3")
          model.pageIn(policy, 0, "4")
}


class Model_Test extends AnyFunSpec with Matchers {
  describe("The Model") {
    describe("can detect correct solutions") {
      it("can check the computed solutions are correct") {
        val fixture = new ModelFixture

        val expectedResult: String =
          s"Current memory state:\n" +
           "Frame 0: 1, 1, 1, 1, 1, 4\n" +
           "Frame 1: -, 2, 2, 2, 2, 2\n" +
           "Frame 2: -, -, 3, 3, 3, 3\n"
        fixture.model.getComputedMemory(Policy.FIFO).showMemory() shouldBe expectedResult
        fixture.model.getComputedMemory(Policy.LRU).showMemory() shouldBe expectedResult
        fixture.model.getComputedMemory(Policy.LRU).showMemory() shouldBe expectedResult
        fixture.model.getComputedMemory(Policy.CLOCK).showMemory() shouldBe expectedResult
      }
      it("can check that the student solutions are as expected") {
        val fixture = new ModelFixture

        val expectedStudentSolution: String =
          s"Frame 0: 1 1 1 1 1 4 \n" +
           "Frame 1: - 2 2 2 2 2 \n" +
           "Frame 2: - - 3 3 3 3 \n"
        for policy <- Policy.values do
          fixture.model.pageIn(policy, 0, "1")
          fixture.model.pageIn(policy, 1, "2")
          fixture.model.pageIn(policy, 2, "3")
          fixture.model.pageIn(policy, 1, "2")
          fixture.model.pageIn(policy, 2, "3")
          fixture.model.pageIn(policy, 0, "4")
        for policy <- Policy.values do
          fixture.model.getStudentSolution(policy).show shouldBe expectedStudentSolution
      }
      it("can check that all student solutions are correct") {
        val fixture = new ModelFixture
        fixture.model.isSolutionCorrect(Policy.FIFO, fixture.model.getStudentSolution(Policy.FIFO).getData) shouldBe true
        fixture.model.isSolutionCorrect(Policy.LRU, fixture.model.getStudentSolution(Policy.FIFO).getData) shouldBe true
        fixture.model.isSolutionCorrect(Policy.CLOCK, fixture.model.getStudentSolution(Policy.FIFO).getData) shouldBe true
        fixture.model.isSolutionCorrect(Policy.OPT, fixture.model.getStudentSolution(Policy.FIFO).getData) shouldBe true
      }
      it("can check that all solutions are identical") {
        val fixture = new ModelFixture
        val correctSet = fixture.model.allCorrectSolutions(fixture.model.getStudentSolutionData(Policy.FIFO))
        correctSet.size shouldBe 4
        // Scala doesn't have a replaceLast so I am abusing reverse and replaceFirst
        val confusionString = correctSet.mkString(", ").reverse.replaceFirst(",", ", or".reverse).reverse
        confusionString shouldBe "OPT, FIFO, Clock, or LRU"
      }
    }
  }
}