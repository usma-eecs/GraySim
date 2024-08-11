package view

import scala.swing._
import scala.swing.event._
import controller.Controller
import model.{Parameters, Policy}

class TableView(view: View, controller: Controller, policy: Policy) extends BorderPanel:
  val numRows = Parameters.getNumFrames + 1 // Frame rows + the row for the Page Request Stream
  val pageRS = controller.getPageRequestStream
  var data = controller.getStudentSolutionData(policy)
  val numCols = pageRS.length + 1 // +1 for the column of labels

  val table = new Table(numRows, numCols) { // scala.swing.Table (a spreadsheet)
    val myTable = this
    rowHeight = Parameters.getLabelHeight
    background = Parameters.parameterBackgroundColor

    val header = {
      import java.awt.event.{MouseEvent, MouseAdapter}

      /*val makeHeaderEvent = TableColumnHeaderSelected(this, _:Int)*/
      val thisTable = peer
      thisTable.addMouseListener(new MouseAdapter() {
        override def mouseClicked(e: MouseEvent) =
          val row = thisTable.rowAtPoint(e.getPoint)
          val column = thisTable.columnAtPoint(e.getPoint)
          if column == 0 && row > 0 then
            enterPageIntoSelectedFrame(row)
            updatePageRequestLabel()
      })
      thisTable
    }


    override def rendererComponent(isSelected: Boolean, focused: Boolean, row: Int, column: Int): Component =
      var thisLabel = "N/A"
      if row == 0 && column == 0 then
        thisLabel = "Page Request Stream"
      else if column == 0 then
        thisLabel = s"Frame ${row-1}"
      else if row == 0 then
        thisLabel = pageRS(column-1).toString()
      else
        thisLabel = data(row-1)(column-1)

      val component = new Label(thisLabel) {
        opaque = true
        // Set the background color based on whether solutions are shown
        background =
          if solutionVisible && column > 0 && row > 0 then
            new Color(200, 255, 200) // Light green for solution cells
          else if column == 0 || row == 0 then
            Parameters.parameterHeadingBackgroundColor // Header cells
          else background // Other cells
        foreground =
          if solutionVisible && column > 0 && row > 0 then
            Parameters.colorBlack // Light red for solution cells
          else if column == 0 || row == 0 then
            Parameters.algorithmTextBoxTextColor // Header cells
          else Parameters.colorBlack // Other cells
        horizontalAlignment = Alignment.Center
      }
      component
  }

  val frameSelection = new BoxPanel(Orientation.Horizontal) {
    val buttons = (0 until numRows - 1).map(i => new RadioButton(s"Frame $i"))
    val group = new ButtonGroup(buttons: _*)
    buttons.foreach(contents += _)
    buttons.head.selected = true // Select the first frame by default
  }

  val pageRequestLabel = new Label(s" Input Page Request ${controller.getNextPageRequest(policy)} into: ") {
    foreground = Parameters.colorBlack
    opaque = true
  }

  val enterButton = new Button("Enter") {
    reactions += {
      case ButtonClicked(_) =>
        enterPageIntoSelectedFrame()
        updatePageRequestLabel()
    }
  }

  val undoButton = new Button("Undo") {
    reactions += {
      case ButtonClicked(_) =>
        undoLastAction()
    }
  }

  layout(new ScrollPane(table)) = BorderPanel.Position.Center
  layout(new BoxPanel(Orientation.Horizontal) {
    contents += pageRequestLabel += frameSelection += enterButton += undoButton
  }) = BorderPanel.Position.South

  def getPanel: BorderPanel = this

  adjustFirstColumnWidth(Parameters.getLabelWidth) // For example, set to 250 pixels

  def adjustFirstColumnWidth(width: Int): Unit =
    if table.peer.getColumnModel.getColumnCount > 0 then
      table.peer.getColumnModel.getColumn(0).setPreferredWidth(width)

  private def enterPageIntoSelectedFrame(row:Int): Unit =
    controller.pageIn(policy, row-1, controller.getNextPageRequest(policy))
    data = controller.getStudentSolutionData(policy)
    updateTableModel

  // Method to enter page number into the selected frame and copy previous values
  private def enterPageIntoSelectedFrame(): Unit =
    val selectedFrame = frameSelection.contents.collectFirst {
      case rb: RadioButton if rb.selected => rb.text.split(" ").last.toInt + 1
    }.getOrElse(1) // Default to the first frame

    controller.pageIn(policy, selectedFrame-1, controller.getNextPageRequest(policy))
    data = controller.getStudentSolutionData(policy)
    updateTableModel

  def undoLastAction(): Unit =
    controller.undo(policy)
    data = controller.getStudentSolutionData(policy)
    updateTableModel
    updatePageRequestLabel()
    repaint()


  // Method to update the page request label with the current page request
  private def updatePageRequestLabel(): Unit =
    pageRequestLabel.text = s"  Input Page Request: ${controller.getNextPageRequest(policy)} into "

  // Method to update the table model with the current data
  private def updateTableModel: Unit =
    for
      rowIndex <- 0 until data.length
      colIndex <- 0 until data(rowIndex).length
    do
      table.update(rowIndex, colIndex, data(rowIndex)(colIndex))
    repaint()

  def updateTableWithSolution(solution: Array[Array[String]]): Unit =
    data = solution
    solutionVisible = true // Show solutions
    updateTableModel
    repaint()

  def hideTableSolution: Unit =
    data = controller.getStudentSolutionData(policy)
    solutionVisible = false // Hide solutions
    updateTableModel
    repaint()

  var solutionVisible = false

  def toggleAnswer =
    if solutionVisible then
      hideAnswer
    else
      showAnswer

  def showAnswer =
    view.hideSolutionButton(policy)
    solutionVisible = true
    Parameters.showAnswers
    view.showAnswers(policy)

  def hideAnswer =
    view.showSolutionButton(policy)
    solutionVisible = false
    Parameters.hideAnswers
    view.hideAnswers(policy)

  def showFeedback =
    controller.checkAndProvideFeedback(policy)
