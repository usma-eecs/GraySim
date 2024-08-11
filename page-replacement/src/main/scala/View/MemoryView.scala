package view

import scala.swing._
import BorderPanel.Position._

import controller.Controller
import model.Policy

/** A MemoryView
 *
 * @constructor
 *   Create a new MemoryView
 * @param controller
 *   Specifies the controller in our MVC
 * @param name
 *   Specifies the name of the replacement policy
 */
class MemoryView(view: View, contr: Controller, policy: Policy) extends BorderPanel:
  visible = true
  val explanationText = new TextPane()
  explanationText.text = "This memory view shows the " + policy.getLongName + " page replacement policy."

  val algorithmPanel = new Algorithm(contr.getAlgorithm(policy))
  val buttonsPanel = new ButtonsPanel(view, policy)
  val tableView = new TableView(view, contr, policy)
  def getTableView: TableView = tableView

  layout += algorithmPanel -> North
  layout += explanationText -> South
  layout += tableView -> Center
  layout += buttonsPanel -> East

  def getPanel = this
  def hideSolutionButton = buttonsPanel.hideSolutionButton
  def showSolutionButton = buttonsPanel.showSolutionButton
  def hideAnswers = tableView.hideAnswer
  def showAnswers = tableView.showAnswer
  def hideEntry = tableView.hideTableSolution
  def showEntry(solution: Array[Array[String]]) = tableView.updateTableWithSolution(solution)
  def toggleAnswer = tableView.toggleAnswer
  def toggleAlgorithmVisibility = algorithmPanel.toggleVisibility
  def toggleFeedbackVisibility = tableView.showFeedback
