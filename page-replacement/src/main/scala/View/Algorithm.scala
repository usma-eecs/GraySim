package view

import scala.swing._
import model.Parameters

class Algorithm(algorithm: String) extends TextPane:
  background = Parameters.getAlgorithmTextboxBackGroundColor
  foreground = Parameters.getAlgorithmTextboxBackGroundColor
  visible = true
  var Visible = false
  text = algorithm

  def init: Unit =
    hideAlgorithm()

  def setAlgorithmString(algorithm: String): Unit =
    text = algorithm

  def showAlgorithm(): Unit =
    Visible = true

  def hideAlgorithm(): Unit =
    Visible = false

  def isVisible: Boolean =
    Visible

  def toggleVisibility: Unit =
    if Visible then
      Visible = false
      foreground = Parameters.getAlgorithmTextboxBackGroundColor
    else
      Visible = true
      foreground = Parameters.getAlgorithmTextboxTextColor
