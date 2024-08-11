package view

import scala.swing._
//import BorderPanel.Position._
//import java.awt.Color
//import controller.Controller
//import model.Model
import model.Parameters

class ProcessTable extends GridPanel(Parameters.getNumProcesses+1, 3):
  private val myLabelSize = new Dimension(Parameters.getLabelWidth, Parameters.getLabelHeight)
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
