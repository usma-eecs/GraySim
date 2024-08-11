package view

import scala.swing._
import java.awt.Color
import model.Parameters
import model.Policy

class ControlButton(showText: String, hideText: String, backgroundColor: Color, controlAction: => Unit) extends Button(showText):
  val buttonSize = new Dimension(Parameters.getButtonWidth, Parameters.getButtonHeight)
  var myVisibility = false
  background = backgroundColor
  foreground = Parameters.buttonControlsTextColor
  opaque = true
  minimumSize = buttonSize
  maximumSize = buttonSize

  action = Action(showText) {
    controlAction
  }

  def toggleButtonLabel: Unit =
    if action.text == showText then
      this.hide
    else
      this.show

  def hide: Unit =
    action.text = hideText

  def show: Unit =
    action.text = showText


class ButtonsPanel(view: View, policy: Policy) extends BoxPanel(Orientation.Vertical):
  val algorithmButton: ControlButton =
    new ControlButton("Show Policy", "Hide Policy", Parameters.algorithmBackgroundColor, view.toggleAlgorithmVisibility(policy))

  val solutionButton: ControlButton =
    new ControlButton("Show Solution", "Hide Solution", Parameters.configurationBackgroundColor, view.toggleAnswer(policy))

  val feedbackButton: ControlButton =
    new ControlButton("Show Feedback", "Hide Feedback", Parameters.solutionBackgroundColor, view.toggleFeedbackVisibility(policy))

  def showSolutionButton: Unit = solutionButton.show
  def hideSolutionButton: Unit = solutionButton.hide
  def showAlgorithmButton: Unit = algorithmButton.show
  def hideAlgorithmButton: Unit = algorithmButton.hide
  def toggleAlgorithmButton: Unit = algorithmButton.toggleButtonLabel
  def showFeedbackButton: Unit = feedbackButton.show
  def hideFeedbackButton: Unit = feedbackButton.hide

  contents += algorithmButton
  contents += feedbackButton
  contents += solutionButton
