package view

import scala.swing._
import java.awt.Color
import model.Parameters
import model.Policy

class ControlButton(showText: String,
                    hideText: String,
                    backgroundColor: Color,
                    controlAction: => Unit) extends Button(showText):
  val buttonSize =
    new Dimension(Parameters.getButtonWidth, Parameters.getButtonHeight)
  var myVisibility = false
  background = backgroundColor
  foreground = Parameters.buttonControlsTextColor
  opaque = true
  minimumSize = buttonSize
  maximumSize = buttonSize

  action = Action(showText) {
    controlAction
  }

  def toggleButtonLabel =
    if action.text == showText then
      this.hide
    else
      this.show

  def hide =
    action.text = hideText

  def show =
    action.text = showText


class ButtonsPanel(view: View, policy: Policy) extends BoxPanel(Orientation.Vertical):
  val algorithmButton: ControlButton =
    new ControlButton(
      "Show Policy",
      "Hide Policy",
      Parameters.algorithmBackgroundColor,
      view.toggleAlgorithmVisibility(policy)
    )
  val solutionButton: ControlButton =
    new ControlButton(
      "Show Solution",
      "Hide Solution",
      Parameters.solutionBackgroundColor,
      view.toggleAnswer(policy)
    )

  val feedbackButton: ControlButton =
    new ControlButton(
      "Show Feedback",
      "Hide Feedback",
      Parameters.feedbackBackgroundColor,
      view.toggleFeedbackWindow(policy)
    )

  def showSolutionButton = solutionButton.show
  def hideSolutionButton = solutionButton.hide

  def showAlgorithmButton = algorithmButton.show
  def hideAlgorithmButton = algorithmButton.hide
  def toggleAlgorithmButton = algorithmButton.toggleButtonLabel

  def showFeedbackButton = feedbackButton.show
  def hideFeedbackButton = feedbackButton.hide

  contents += algorithmButton
  contents += feedbackButton
  contents += solutionButton
