package view

import scala.collection.mutable.Map
import scala.swing._
import controller.Controller
import model.Policy
import model.Parameters

class View extends MainFrame:
  var _controller: Option[Controller] = None

  // Create feedback dialog
  val feedbackDialog = new FeedbackDialog(this)

  // Create the tabbed pane that will hold everything
  var tabbedPane: TabbedPane = null
  var tablePanel: TableView = null
  var optPanel: MemoryView = null
  var fifoPanel: MemoryView = null
  var lruPanel: MemoryView = null
  var clockPanel: MemoryView = null
  var memoryViews: Map[Policy, MemoryView] = Map.empty[Policy, MemoryView]

  /** View.init
    * @param controller
    */
  def init(controller: Controller): Unit =
    _controller = Some(controller)
    title = "GraySim Page Replacement Practice"

    centerOnScreen()

    menuBar = new MenuBar {
      contents += new Menu("Controls") {
        contents += new MenuItem(controller.exit)
      }
    } // end MenuBar

    memoryViews += (Policy.OPT, new MemoryView(this, _controller.get, Policy.OPT))
    memoryViews += (Policy.FIFO, new MemoryView(this, _controller.get, Policy.FIFO))
    memoryViews += (Policy.LRU, new MemoryView(this, _controller.get, Policy.LRU))
    memoryViews += (Policy.CLOCK, new MemoryView(this, _controller.get, Policy.CLOCK))

    tabbedPane = new TabbedPane()

    for policy <- Policy.values do
      tabbedPane.pages += new TabbedPane.Page(policy.getShortName, memoryViews(policy).getPanel, policy.getLongName)

    contents = tabbedPane

    size = new Dimension(
       Parameters.getMainFrameWidth,
       Parameters.getMainFrameHeight(false)
    )
    visible = true

  def giveFeedback(feedbackInfo: String) =
    feedbackDialog.showFeedback(feedbackInfo)

  def toggleAlgorithmVisibility(policy: Policy) =
    memoryViews(policy).toggleAlgorithmVisibility

  def toggleAnswer(policy: Policy) =
    memoryViews(policy).toggleAnswer

  def toggleFeedbackVisibility(policy: Policy) =
    memoryViews(policy).toggleFeedbackVisibility

  def showSolutionButton(policy: Policy) =
    memoryViews(policy).showSolutionButton

  def hideSolutionButton(policy: Policy) =
    memoryViews(policy).hideSolutionButton

  def hideAnswers(policy: Policy) =
    memoryViews(policy).hideEntry

  /** View.showAnswers
    */
  def showAnswers(policy: Policy) =
    val solutions: Map[Policy, Array[Array[String]]] = Map(
      Policy.OPT -> _controller.get.getComputedSolution(Policy.OPT),
      Policy.FIFO -> _controller.get.getComputedSolution(Policy.FIFO),
      Policy.LRU -> _controller.get.getComputedSolution(Policy.LRU),
      Policy.CLOCK -> _controller.get.getComputedSolution(Policy.CLOCK)
    )
    val solution = solutions(policy)
    memoryViews(policy).showEntry(solution)

  def showAnswer(policy: Policy) =
    memoryViews(policy).showAnswers
  def hideAnswer(policy: Policy) =
    memoryViews(policy).hideAnswers
