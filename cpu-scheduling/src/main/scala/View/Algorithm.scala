package view

import model.Parameters
import scala.swing._
import java.awt.Color
import java.lang.reflect.Parameter
import javax.xml.crypto.AlgorithmMethod

class Algorithm(view: View, algorithm: String) extends TextPane:
  background = Parameters.getAlgorithmTextboxBackGroundColor
  foreground = Parameters.getAlgorithmTextboxBackGroundColor
  visible = true // this controls whether the TextPane is visible
  var algVisible = false // this controls whether the algorithm is visible
  text = algorithm
  //preferredSize = new Dimension(750, 100)

  def init =
    hideAlgorithm()

  def setAlgorithmString(algorithm: String) =
    text = algorithm

  def showAlgorithm() =
    algVisible = true

  def hideAlgorithm() =
    algVisible = false

  def isVisible: Boolean =
    algVisible

  def toggleVisibility: Unit =
    if algVisible then
      algVisible = false
      foreground = Parameters.getAlgorithmTextboxBackGroundColor
    else
      algVisible = true
      foreground = Parameters.getAlgorithmTextboxTextColor


class AlgorithmsPanel(view: View, algorithm: String) extends BoxPanel(Orientation.Horizontal):
  var algorithmText: Algorithm = new Algorithm(view, algorithm)

  val image = javax.imageio.ImageIO.read(new java.io.File("resources/Pseudocode.png"))
  var pseudoCodey = new Panel:
    override def paint(g: Graphics2D): Unit = g.drawImage(image,0,0,null)
    preferredSize = new Dimension(140, 100)

  def toggleVisibility = algorithmText.toggleVisibility

  contents += algorithmText
  if Parameters.showPseudoCode then contents += pseudoCodey
