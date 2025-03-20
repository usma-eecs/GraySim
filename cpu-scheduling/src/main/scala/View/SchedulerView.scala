package view

import scala.swing._
import BorderPanel.Position._
import controller.Controller
import model.Parameters
import model.Policy

/** A SchedulerView
  *
  * @constructor
  *   Create a new SchedulerView
  * @param controller
  *   Specifies the controller in our MVC
  * @param name
  *   Specifies the name of the scheduling policy
  */
class SchedulerView(view: View, contr: Controller, policy: Policy) extends BorderPanel:
  visible = true
  var explanationText = new TextPane()
  explanationText.text =
    s"This scheduler view shows the ${policy.getLongName} scheduling policy."

  val algorithmPanel = new AlgorithmsPanel(view, contr.getAlgorithm(policy))
  val buttonsPanel = new ButtonsPanel(view, policy)
  var processTable = new ProcessTable()
  var schedulingPlan = new SchedulingPlan(view, contr, policy)

  layout += algorithmPanel -> North
  layout += explanationText -> South
  layout += processTable -> West
  layout += schedulingPlan -> Center
  layout += buttonsPanel -> East

  def getPanel = this
  def hideSolutionButton = buttonsPanel.hideSolutionButton
  def showSolutionButton = buttonsPanel.showSolutionButton
  def hideAnswers = schedulingPlan.hideAnswer
  def showAnswers = schedulingPlan.showAnswer
  def hideEntry(row: Int, column: Int) = schedulingPlan.hideEntry(row, column)
  def showEntry(row: Int, column: Int) = schedulingPlan.showEntry(row, column)
  def toggleAnswer = schedulingPlan.toggleAnswer
  def toggleAlgorithmVisibility =
    algorithmPanel.toggleVisibility
    schedulingPlan.toggleAlgorithmButtonVisibility
  def hideAlgorithmButton = buttonsPanel.hideAlgorithmButton
  def showAlgorithmButton = buttonsPanel.showAlgorithmButton
  def toggleFeedbackWindow = schedulingPlan.toggleFeedbackWindow
  def resetEntry(row: Int, column: Int) =
    schedulingPlan.reset(row, column)

  override def repaint() =
    super.repaint()

  def refreshSchedulerView =
    processTable = new ProcessTable()
    processTable.refreshTable
    layout += processTable -> West
    schedulingPlan = new SchedulingPlan(view, contr, policy)
    schedulingPlan.repaint()
    layout += schedulingPlan -> Center
    algorithmPanel.hide
    showAlgorithmButton
    showSolutionButton
    this.repaint()
