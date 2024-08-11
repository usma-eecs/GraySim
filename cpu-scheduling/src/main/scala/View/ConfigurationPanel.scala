package view

import scala.collection.mutable.Map
import scala.swing._
import BorderPanel.Position._
import controller.Controller
import model.Parameters

/** A BlankPanel
 *
 * @constructor
 *   Create a new BlankPanel
 * @param label
 *   Specifies the text that goes into the underlying text pane
 * @param width
 *   Specifies the minimum width of the panel
 * @param height
 *   Specifies the minimum height of the panel
 * @param color
 *   Specifies the color of the panel
 */
class BlankPanel(label: String, width: Int, height: Int, color: Color) extends TextPane():
  val myDimension = new Dimension(width, height)
  text = label
  visible = true
  minimumSize = myDimension
  background = color


/** A ConfigurationPanel
 *
 * @constructor
 *   Create a new ConfigurationPanel
 * @param controller
 *   Specifies the controller in our MVC
 */
class ConfigurationPanel(contr: Controller) extends BorderPanel:
  private val myLabelSize = new Dimension(Parameters.getLabelWidth, Parameters.getLabelHeight)

  var explanationText = new TextPane()
  explanationText.text = "The configuration view shows the configuration panel."

  private val myWestBox = new BlankPanel("West Box", Parameters.getVerticalWidth, Parameters.getVerticalHeight, Parameters.parameterHeadingBackgroundColor)
  private val myEastBox = new BlankPanel("East Box", Parameters.getVerticalWidth, Parameters.getVerticalHeight, Parameters.parameterHeadingBackgroundColor)
  private val myNorthBox = new BlankPanel("North Box", Parameters.getHorizontalWidth, Parameters.getHorizontalHeight, Parameters.parameterHeadingBackgroundColor)
  private val mySouthBox = new BlankPanel("South Box", Parameters.getHorizontalWidth, Parameters.getHorizontalHeight, Parameters.parameterHeadingBackgroundColor)

  object processPanel extends GridPanel(Parameters.getNumProcesses+1, 3):
    visible = true
    // Create the labels for the top row of the process panel
    contents += new Label("Process") {
      background = Parameters.parameterHeadingBackgroundColor
      foreground = Parameters.parameterHeadingTextColor
      minimumSize = myLabelSize
      opaque = true
    }
    contents += new Label("  Start Time  ") {
      background = Parameters.parameterHeadingBackgroundColor
      foreground = Parameters.parameterHeadingTextColor
      opaque = true
    }
    contents += new Label("  Service Time  ") {
      background = Parameters.parameterHeadingBackgroundColor
      foreground = Parameters.parameterHeadingTextColor
      opaque = true
    }
    // For each process, put the name in the left-most column, the start time
    // in the middle column, and the service time in the right-most column
    for i <- 0 until Parameters.getNumProcesses do
      contents += new Label(Parameters.processNames(i)) {
        background =
          Parameters.parameterHeadingBackgroundColor // parameterBackgroundColor
        foreground = Parameters.parameterHeadingTextColor // parameterTextColor
        opaque = true
      }
      contents += new Label(Parameters.processStartTimes(i).toString) {
        background = Parameters.parameterBackgroundColor
        foreground = Parameters.parameterTextColor
        opaque = true
      }
      contents += new Label(Parameters.processServiceTimes(i).toString) {
        background = Parameters.parameterBackgroundColor
        foreground = Parameters.parameterTextColor
        opaque = true
      }

  layout += explanationText -> North
  layout += processPanel -> Center
  layout += myWestBox -> West
  layout += myEastBox -> East
  layout += mySouthBox -> South
  visible = true

  def getPanel = this
