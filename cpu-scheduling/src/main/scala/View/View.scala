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
//        contents += new MenuItem(controller.showAnswer)
//        contents += new MenuItem(controller.toggleAlgorithmVisibility)
//        contents += new Separator
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
  /*
      pages += new TabbedPane.Page(
        "FIFO",
        fifoPanel.getPanel,
        "This tab shows the First In First Out scheduling algorithm."
      )
      pages += new TabbedPane.Page(
        "SJF",
        sjfPanel.getPanel,
        "This tab shows the Shortest Process Next scheduling algorithm."
      )
      pages += new TabbedPane.Page(
        "STCF",
        stcfPanel.getPanel,
        "This tab shows the Shortest Remaining Time scheduling algorithm."
      )
      pages += new TabbedPane.Page(
        "RR",
        rrPanel.getPanel,
        "This tab shows the Round Robin scheduling algorithm."
      )
      pages += new TabbedPane.Page(
        "MLFQ",
        mlfqPanel.getPanel,
        "This tab shows the Multi-Level Feedback Queue scheduling algorithm."
      )
    }
*/
    contents = tabbedPane

    size = new Dimension(
      Parameters.getMainFrameWidth,
      Parameters.getMainFrameHeight(false)
    )
    visible = true

  def giveFeedback(feedbackInfo: String) =
    feedbackDialog.showFeedback(feedbackInfo)

  def toggleAlgorithmVisibility(policy: Policy) =
    schedulerViews(policy).toggleAlgorithmVisibility
    //print("Show policy?\n")

  def toggleAnswer(policy: Policy) =
    schedulerViews(policy).toggleAnswer
    //print("Show solution?\n")

  def toggleFeedbackWindow(policy: Policy) =
    schedulerViews(policy).toggleFeedbackWindow
    //print("TODO: Show feedback window")

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
