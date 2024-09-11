package view

import scala.swing._
import BorderPanel.Position._
import controller.Controller
import model.Model
import model.Parameters
import model.Policy
import scala.compiletime.ops.double

class SchedulingPlan(view: View, controller: Controller, policy: Policy)
      extends GridPanel(Parameters.getNumProcesses + 1,
                        Parameters.getTotalServiceTime
                          + Parameters.getNumProcesses + 1):

  var schedulingPlan: Array[Array[Button]] = Array
    .ofDim[Button](Parameters.getMaxProcesses, Parameters.getTotalServiceTime)
  var solutionVisible = false
  var algorithmButtonVisible = true

  // Initialize the scheduling Plan buttons
  for row <- 0 until Parameters.getMaxProcesses do
    for column <- 0 until Parameters.getTotalServiceTime do
      schedulingPlan(row)(column) = new Button() {
        background = Parameters.buttonBackgroundColor
        foreground = Parameters.buttonBackgroundColor
        opaque = true
        action = Action("") {
          if this.background == Parameters.buttonSelectedColor then
            this.background = Parameters.buttonBackgroundColor
            controller.unrecordStudentScheduleEntry(
              policy,
              Parameters.processNames(row),
              column
            )
          else
            this.background = Parameters.buttonSelectedColor
            controller.recordStudentScheduleEntry(
              policy,
              Parameters.processNames(row),
              column
            )

          if Parameters.areAnswersVisible then
            this.foreground = Parameters.buttonTextVisibleColor
          else
            this.foreground = this.background
        }
      }

  // Initialize the grid panel
  val myLabelSize = new Dimension(Parameters.getLabelWidth, Parameters.getLabelHeight)

  for row <- 1 to Parameters.getNumProcesses+1 do
    if row == 1 then
      contents += new Label("Process") {
        background = Parameters.headingColor
        foreground = Parameters.headingTextColor
        minimumSize = myLabelSize
        opaque = true
      }
    else
      contents += new Label(Parameters.processNames(row-2)) {
        background = Parameters.headingColor
        foreground = Parameters.headingTextColor
        opaque = true
      }
    for column <- 1 to Parameters.getTotalServiceTime do
      if row == 1 then
        val columnLabel = column - 1
        contents += new Label(columnLabel.toString) {
          background = Parameters.headingColor
          foreground = Parameters.headingTextColor
          opaque = true
        }
      else contents += schedulingPlan(row - 2)(column - 1)

  def showEntry(row: Int, column: Int): Unit =
    schedulingPlan(row)(column).text = "X"
    schedulingPlan(row)(column).foreground = Parameters.buttonTextVisibleColor

  def hideEntry(row: Int, column: Int): Unit =
    schedulingPlan(row)(column).text = ""
    schedulingPlan(row)(column).foreground =
      schedulingPlan(row)(column).background

  def toggleAnswer =
    if solutionVisible then
      hideAnswer
    else
      showAnswer

  def toggleAlgorithmButtonVisibility =
    if algorithmButtonVisible then
      hideAlgorithmButton
    else
      showAlgorithmButton

  def toggleFeedbackWindow =
    showFeedback

  def showAnswer =
    view.hideSolutionButton(policy)
    solutionVisible = true
    Parameters.showAnswers
    view.showAnswers(policy)
    //controller.giveFeedback(policy)

  def hideAnswer =
    view.showSolutionButton(policy)
    solutionVisible = false
    Parameters.hideAnswers
    view.hideAnswers(policy)

  def hideAlgorithmButton = //change to hide
    view.hideAlgorithmButton(policy)
    algorithmButtonVisible = false
    //println("hiding algorithmButton")


  def showAlgorithmButton = //change to show
    view.showAlgorithmButton(policy)
    algorithmButtonVisible = true
    //println("showing algorithmButton")


  def showFeedback =
    controller.giveFeedback(policy)
