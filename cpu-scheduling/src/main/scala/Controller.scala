package controller

import scala.swing._
import model._
import view._

class Controller(view: View, model: Model):

  def init = {}

  def showAnswer(policy: Policy) = Action("Show Answer") {
    view.showAnswer(policy)
  }

  def toggleAlgorithmVisibility(policy: Policy) = Action("Toggle Algorithm Visibility") {
    view.toggleAlgorithmVisibility(policy)
  }

  def getAlgorithm(policy: Policy): String =
    model.getSchedulingAlgorithm(policy)

  def getProcessScheduled(policy: Policy, time: Int): Char = model.getProcessScheduled(policy, time)

  def isCorrectSolution(policy: Policy): Boolean =
    model.isCorrectSolution(policy)

  /** Controller.checkAnswers
   */
  def checkAnswers(policy: Policy, plan: StudentSolution): Int =
    var numCorrect = 0
    val numRows = Parameters.getMaxProcesses
    val numColumns = Parameters.getTotalServiceTime-1
    for row <- 0 until Parameters.getMaxProcesses do
      for column <- 0 until Parameters.getTotalServiceTime do
        if model.isStudentSolutionEntrySelected(policy, row, column)
            && plan.isScheduled(row, column) then
          numCorrect +=1
    numCorrect

  def giveFeedback(policy: Policy) =
    if model.isCorrectSolution(policy) then
      view.giveFeedback("Congratulations! Everything is correct!")
    else
      view.giveFeedback(model.provideFeedback(policy))

  def recordStudentScheduleEntry(policy: Policy, process: String, timeSlice: Int) =
    model.setStudentScheduleEntry(policy, process, timeSlice)

  def unrecordStudentScheduleEntry(policy: Policy, process: String, timeSlice: Int) =
    model.unsetStudentScheduleEntry(policy, process, timeSlice)

  def getStudentSolutionEntry(policy: Policy, row: Int, column: Int): Char =
    model.getStudentSolutionEntry(policy, row, column)

  def exit = Action("Exit") {
    sys.exit(0)
  }
