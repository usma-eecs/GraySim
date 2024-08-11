package model

import java.awt.Color

object Parameters:

  private var verbose = false
  private var numFrames = 3
  private var answersVisible = false

  def setVerbose: Unit =
    verbose = true

  def areAnswersVisible: Boolean = answersVisible
  def showAnswers: Unit =
    answersVisible = true
  def hideAnswers: Unit =
    answersVisible = false

  val headingColor = new Color(11, 4, 66)
  val headingTextColor = new Color(202, 198, 233)
  val buttonBackgroundColor = new Color(231,230,236)
  val buttonSelectedColor = new Color(109,104,142)
  val buttonTextVisibleColor = new Color(11, 4, 66) // 208, 30, 19

  val parameterHeadingBackgroundColor = new Color(6, 41, 24) // darker color
  val parameterHeadingTextColor = new Color(196, 245, 220) // lighter color
  val parameterBackgroundColor = new Color(230, 252, 243) // 87, 213, 151)
  val parameterTextColor = new Color(5, 33, 20)

  val buttonControlsTextColor = new Color(5, 33, 20)
  val algorithmBackgroundColor = new Color(247, 204, 186)
  val solutionBackgroundColor = new Color(247, 188, 164)
  val configurationBackgroundColor = new Color(247, 176, 146)

  val algorithmTextBoxTextColor = new Color(247,218,221)
  val algorithmTextBoxBackgroundColor = new Color(99,3,13)
  def getAlgorithmTextboxTextColor = algorithmTextBoxTextColor
  def getAlgorithmTextboxBackGroundColor = algorithmTextBoxBackgroundColor

  val colorBlack = new Color(0,0,0)

  val buttonWidth = 150
  val buttonHeight = 175

  def getButtonWidth: Int = buttonWidth
  def getButtonHeight: Int = buttonHeight

  val heightOfTabs = 50
  val heightPerFrameRow = 54
  val heightOfAlgorithm = 50
  val heightOfInput = 50
  val heightOfLabel = 50
  val widthFrameLabel = 300
  val widthPerPageRequestColumn = 100
  val widthOfFrameDetailsTable = 0
  val widthOfControlButtons = 175

  def getLabelWidth = widthFrameLabel
  def getLabelHeight = heightPerFrameRow

  val verticalBoxWidth = 4*widthFrameLabel
  val verticalBoxHeight = heightPerFrameRow * numFrames
  val horizontalBoxWidth = widthOfFrameDetailsTable + widthOfControlButtons*2
  val horizontalBoxHeight = heightOfAlgorithm*10
  def getVerticalWidth = verticalBoxWidth
  def getVerticalHeight = verticalBoxHeight
  def getHorizontalWidth = horizontalBoxWidth
  def getHorizontalHeight = horizontalBoxHeight

  var totalWidth = -1
  var totalHeight = -1

  def setNumFrames(num: Int): Unit =
    numFrames = num

  def getNumFrames: Int = numFrames

  def calculateMainFrameDimensions: Unit =
    totalWidth = widthOfFrameDetailsTable +
      (widthPerPageRequestColumn * numFrames) +
      widthFrameLabel +
      widthOfControlButtons
    totalHeight = heightOfTabs +
      heightOfAlgorithm +
      (heightPerFrameRow * (numFrames + 1)) +
      heightOfInput +
      heightOfLabel

  def getMainFrameHeight(algorithmVisible: Boolean): Int =
    calculateMainFrameDimensions
    if algorithmVisible then
      totalHeight
    else
      totalHeight + heightOfAlgorithm

  def getMainFrameWidth: Int =
    calculateMainFrameDimensions
    totalWidth + 100

  def validateOptions(): Boolean =
    calculateMainFrameDimensions
    true

