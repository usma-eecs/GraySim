package controller

import scala.swing._
import model._
import view._

class Controller(view: View, model: Model):

  def init = {}

  def getPageRequestStream: Array[Int] =
    model.getPageRequestStream().getPageRequestStream

  def getNumPageRequests: Int = model.getNumPageRequests

  def showAnswer(policy: Policy) = Action("Show Answer") {
    view.showAnswer(policy)
  }

  def toggleAlgorithmVisibility(policy: Policy) = Action("Toggle Algorithm Visibility") {
    view.toggleAlgorithmVisibility(policy)
  }

  def getAlgorithm(policy: Policy): String =
    model.getSchedulingAlgorithm(policy)

  def getStudentSolution(policy:Policy): StudentSolution =
    model.getStudentSolution(policy)

  def getStudentSolutionData(policy:Policy): Array[Array[String]] =
    model.getStudentSolutionData(policy)

  def getNextPageRequest(policy: Policy): String =
    model.getStudentSolution(policy).getNextPageRequest(model.getPageRequestStream())

  def pageIn(policy: Policy, frame: Int, page: String): Unit =
    model.pageIn(policy, frame, page)

  def undo(policy: Policy): Unit =
    model.undo(policy)

  def checkAndProvideFeedback(policy: Policy): Unit =
    val studentSolution = model.getStudentSolution(policy)
    val isCorrect = model.isSolutionCorrect(policy, studentSolution.getData)  // Compare it with the correct solution
    if isCorrect then
      view.giveFeedback("Congratulations! Everything is correct!")
    else
      view.giveFeedback(model.provideFeedback(policy, model.allCorrectSolutions(studentSolution.getData)))

  def getComputedSolution(policy: Policy): Array[Array[String]] =
    model.getComputedSolution(policy)

  def exit = Action("Exit") {
    println("***EXIT***")
    sys.exit(0)
  }
