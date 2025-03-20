package view

import scala.collection.mutable.Map
import scala.swing._
import BorderPanel.Position._
import java.awt.Color
import controller.Controller
import model.Model
import model.Parameters
import model.Policy

class View extends MainFrame:
  var _controller: Option[Controller] = None

  // Create feedback dialog
  val feedbackDialog = new FeedbackDialog(this)

  // Create the tabbed pane that will hold everything
  var tabbedPane: TabbedPane = null
  var processConfigurationPanel: ConfigurationPanel = null
  var fifoPanel: SchedulerView = null
  var sjfPanel: SchedulerView = null
  var stcfPanel: SchedulerView = null
  var rrPanel: SchedulerView = null
  var mlfqPanel: SchedulerView = null
  var schedulerViews: Map[Policy, SchedulerView] = Map.empty[Policy, SchedulerView]

  /** View.init
    * @param controller
    */
  def init(controller: Controller): Unit =
    _controller = Some(controller)
    title = "GraySim Scheduling Practice"

    centerOnScreen()

    menuBar = new MenuBar {
      contents += new Menu("Controls") {
        contents += new MenuItem(Action("New Practice Problem") {
          _controller.get.newProblem
        })
        contents += new Separator
        contents += new MenuItem(controller.exit)
      }
    } // end MenuBar

    processConfigurationPanel = ConfigurationPanel(_controller.get)
    schedulerViews += (Policy.FIFO, new SchedulerView(this, _controller.get, Policy.FIFO))
    schedulerViews += (Policy.SJF, new SchedulerView(this, _controller.get, Policy.SJF))
    schedulerViews += (Policy.STCF, new SchedulerView(this, _controller.get, Policy.STCF))
    schedulerViews += (Policy.RR, new SchedulerView(this, _controller.get, Policy.RR))
    schedulerViews += (Policy.MLFQ, new SchedulerView(this, _controller.get, Policy.MLFQ))

    tabbedPane = new TabbedPane() {
      pages += new TabbedPane.Page(
        "Processes",
        processConfigurationPanel.getPanel,
      )
      //TODO: This needs to be changed somehow (I can still see "South Pane", etc.)
    }
    for policy <- Policy.values do
      tabbedPane.pages += new TabbedPane.Page(policy.getShortName, schedulerViews(policy).getPanel, policy.getLongName)
    contents = tabbedPane

    size = new Dimension(
      Parameters.getMainFrameWidth,
      Parameters.getMainFrameHeight(false)
    )
    visible = true

  def resetButtons =
    for policy <- Policy.values do
      schedulerViews(policy).hideAnswers
      schedulerViews(policy).hideAlgorithmButton

  def giveFeedback(feedbackInfo: String) =
    feedbackDialog.showFeedback(feedbackInfo)

  def toggleAlgorithmVisibility(policy: Policy) =
    schedulerViews(policy).toggleAlgorithmVisibility

  def showAlgorithmButton(policy: Policy) =
    schedulerViews(policy).showAlgorithmButton

  def hideAlgorithmButton(policy: Policy) =
    schedulerViews(policy).hideAlgorithmButton

  def toggleAnswer(policy: Policy) =
    schedulerViews(policy).toggleAnswer

  def toggleFeedbackWindow(policy: Policy) =
    schedulerViews(policy).toggleFeedbackWindow

  def showSolutionButton(policy: Policy) =
    schedulerViews(policy).showSolutionButton

  def hideSolutionButton(policy: Policy) =
    schedulerViews(policy).hideSolutionButton

  def hideAnswers(policy: Policy) =
    for column <- 0 until Parameters.getTotalServiceTime do
      var row = _controller.get.getProcessScheduled(policy, column) - 'A'
      schedulerViews(policy).hideEntry(row,column)

  /** View.showAnswers
    */
  def showAnswers(policy: Policy) =
    for column <- 0 until Parameters.getTotalServiceTime do
      var row = _controller.get.getProcessScheduled(policy, column) - 'A'
      schedulerViews(policy).showEntry(row, column) //shows one answer...

  def showAnswer(policy: Policy) =
    schedulerViews(policy).showAnswers
  def hideAnswer(policy: Policy) =
    schedulerViews(policy).hideAnswers

  def updateStudentAnswers(policy: Policy) =
    var sView = schedulerViews(policy)
    for row <- 0 until Parameters.getNumProcesses do
      for column <- 0 until Parameters.getTotalServiceTime do
        sView.resetEntry(row,column)

  def reset(policy: Policy): Unit =
    _controller.get.reset(policy)
    schedulerViews(policy).refreshSchedulerView
    updateStudentAnswers(policy)

  def reset() : Unit =
    processConfigurationPanel.refreshConfigurationPanel
    for policy <- Policy.values do
      reset(policy)
