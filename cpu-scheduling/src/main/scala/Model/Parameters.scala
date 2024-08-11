package model

import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer
import java.awt.Color
import scala.util.Random
import model.Policy

object Parameters:

  private var ordering: Ordering[Process] =
    Ordering.by[Process, Int](_.getStartTime).reverse
  private var policy: Policy = Policy.FIFO
  private var verbose = false
  private var fcfs = true
  private var rr = false
  private val maxQuantum = 4
  private val maxTotalServiceLength = 40
  private var quantum = 1
  val maxProcesses = 10
  private val defaultNumProcesses = 5
  private val defaultServiceLength = 3
  private var numProcesses = 0
  private var totalServiceTime = 0
  private var answersVisible = false

  def setVerbose =
    verbose = true

  def areAnswersVisible = answersVisible
  def showAnswers =
    answersVisible = true
  def hideAnswers =
    answersVisible = false

  def getPolicy: Policy =
    policy

  def setPolicy(p: Policy) =
    policy = p
    p match
      case Policy.FIFO =>
        ordering = Ordering.by[Process, Int](_.getStartTime).reverse
      case Policy.RR =>
        ordering = Ordering.by[Process, Int](_.getStartTime).reverse
      case Policy.SJF =>
        ordering = Ordering
          .by[Process, (Int, Int)](p => (p.getServiceTime, p.getStartTime))
          .reverse
      case Policy.STCF =>
        ordering = Ordering
          .by[Process, (Int, Int)](p => (p.getProcessingTime, p.getStartTime))
          .reverse
      case Policy.MLFQ =>
        ordering = Ordering.by[Process, Int](_.getStartTime).reverse

  def getOrdering =
    ordering

  def getProcessQueueOrdering: Ordering[Process] =
    Ordering.by[Process, Int](_.getStartTime).reverse

  def getProcessName(i: Int): String =
    assert(i < maxProcesses)
    processNames(i)

  def getMaxProcesses = maxProcesses

  def getNumProcesses = numProcesses

  def getProcessStartTime(processNum: Int): Int =
    Parameters.processStartTimes(processNum)

  def setProcessStartTime(processNum: Int, time: Int) =
    Parameters.processStartTimes(processNum) = time

  def setProcessServiceTime(processNum: Int, time: Int) =
    Parameters.processServiceTimes(processNum) = time

  def getProcessServiceTime(processNum: Int): Int =
    Parameters.processServiceTimes(processNum)

  def setTotalServiceTime(time: Int) =
    totalServiceTime = time

  def getTotalServiceTime = totalServiceTime

  def getQuantum =
    quantum

  def setQuantum(n: Int) =
    if n <= maxQuantum then quantum = n
    else
      println(s"Invalid Parameter: Quantum cannot exceed $maxQuantum")
      sys.exit(-1)

  val processNames = Array("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
  val processServiceTimes = ArrayBuffer(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
  val processStartTimes = ArrayBuffer(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1)

  def resetProcessData =
    numProcesses = 0
    for i <- 1 until processNames.length do
      processServiceTimes(i) = 0
      processStartTimes(i) = -1

  val showPseudoCode = false

  val headingColor = new Color(11, 4, 66)
  val headingTextColor = new Color(202, 198, 233)
  val buttonBackgroundColor = new Color(231,230,236)
  val buttonSelectedColor = new Color(109,104,142)
  val buttonTextVisibleColor = new Color(11, 4, 66) // (208, 30, 19)

  val parameterHeadingBackgroundColor = new Color(6, 41, 24) // darker color
  val parameterHeadingTextColor = new Color(196, 245, 220) // lighter color
  val parameterBackgroundColor = new Color(230, 252, 243) // (87, 213, 151)
  val parameterTextColor = new Color(5, 33, 20)

  val buttonControlsTextColor = new Color(5, 33, 20)
  val algorithmBackgroundColor = new Color(247, 204, 186)
  val solutionBackgroundColor = new Color(247, 176, 146)
  val feedbackBackgroundColor = new Color(247, 188, 164)

  val algorithmTextBoxTextColor = new Color(247,218,221)
  val algorithmTextBoxBackgroundColor = new Color(99,3,13)
  def getAlgorithmTextboxTextColor = algorithmTextBoxTextColor
  def getAlgorithmTextboxBackGroundColor = algorithmTextBoxBackgroundColor

  val buttonWidth = 150
  val buttonHeight = 175

  def getButtonWidth = buttonWidth
  def getButtonHeight = buttonHeight

  val heightPerProcessRow = 40
  val heightOfAlgorithm = 100
  val widthProcessLabel = 300
  val widthPerServiceTimeColumn = 150
  val widthOfProcessDetailsTable = 275
  val widthOfControlButtons = 175

  def getLabelWidth = widthProcessLabel
  def getLabelHeight = heightPerProcessRow

  val verticalBoxWidth = 4*widthProcessLabel
  val verticalBoxHeight = heightPerProcessRow * numProcesses
  val horizontalBoxWidth = widthOfProcessDetailsTable + widthOfControlButtons*2
  val horizontalBoxHeight = heightOfAlgorithm*10
  def getVerticalWidth = verticalBoxWidth
  def getVerticalHeight = verticalBoxHeight
  def getHorizontalWidth = horizontalBoxWidth
  def getHorizontalHeight = horizontalBoxHeight

  var totalWidth = -1
  var totalHeight = -1

  def calculateMainFrameDimensions: Unit =
    totalWidth = widthOfProcessDetailsTable +
      (widthPerServiceTimeColumn * numProcesses) +
      widthProcessLabel +
      widthOfControlButtons
    totalHeight = (heightPerProcessRow * (numProcesses + 1))

  def getMainFrameHeight(algorithmVisible: Boolean) =
    if (algorithmVisible)
      totalHeight
    else
      totalHeight + heightOfAlgorithm

  def getMainFrameWidth =
    totalWidth + 100


  private val randomization: Array[Array[(Int,Int)]] = Array(
    Array((0,9), (2,6), (6,3)),
    Array((0,4), (4,7), (5,7)),
    Array((0,5), (1,3), (2,4)),
    Array((0,5), (2,2), (3,1), (8,2)),
    Array((0,5), (2,5), (3,5), (8,5)),
    Array((0,2), (1,8), (5,4), (7,3), (9,1)),
    Array((0,6), (1,3), (2,2), (3,7), (4,1)),
    Array((0,4), (0,8), (1,1), (4,8), (7,5))
  )

  private def activateRandomization: Unit =
    resetProcessData
    val arr = randomization(Random.nextInt(randomization.size))
    for index <- 0 until arr.size do
      setProcessStartTime(index, arr(index)._1)
      setProcessServiceTime(index, arr(index)._2)

    var map: Map[String, Any] = Map()
    validateOptions()

  def validateOptions(): Boolean =
    totalServiceTime = 0
    for (i <- 0 to processNames.length - 1)
      totalServiceTime += processServiceTimes(i)
    if (totalServiceTime > maxTotalServiceLength)
      println(
        s"Invalid Parameters: Total service time of $totalServiceTime cannot exceed $maxTotalServiceLength"
      )
    for (i <- 0 to processNames.length - 1)
      if (processServiceTimes(i) > 0)
        if (processStartTimes(i) >= totalServiceTime)
          println(
            s"Invalid Parameters: Process ${processNames(i)} starts after the total service time."
          )
          sys.exit(-1)
        numProcesses += 1

    if totalServiceTime == 0 then //if no processes entered, then here they are implemented
      activateRandomization

    //TODO: need to validate if processes overlap. Cannot have open spots between processes....?

    calculateMainFrameDimensions
    true


  def twoJobsArriveAtSameTime: Unit =
    resetProcessData
    setProcessStartTime(0, 0)
    setProcessServiceTime(0, 5)
    setProcessStartTime(1, 0)
    setProcessServiceTime(1, 3)
    var map: Map[String, Any] = Map()
    validateOptions()

  def oneJobStartsBeforeAnother: Unit =
    resetProcessData
    setProcessStartTime(0, 1)
    setProcessServiceTime(0, 5)
    setProcessStartTime(1, 0)
    setProcessServiceTime(1, 3)
    var map: Map[String, Any] = Map()
    validateOptions()

  def delaysBeforeAndBetweenJobs: Unit =
    resetProcessData
    setProcessStartTime(0, 7)
    setProcessServiceTime(0, 5)
    setProcessStartTime(1, 1)
    setProcessServiceTime(1, 3)
    var map: Map[String, Any] = Map()
    validateOptions()

  def overlapBetweenJobs: Unit =
    resetProcessData
    setProcessStartTime(0, 0)
    setProcessServiceTime(0, 5)
    setProcessStartTime(1, 1)
    setProcessServiceTime(1, 3)
    setProcessStartTime(2, 2)
    setProcessServiceTime(2, 4)
    var map: Map[String, Any] = Map()
    validateOptions()

  def givenTests: Unit =
    resetProcessData
    setProcessStartTime(0, 0)
    setProcessServiceTime(0, 4)
    setProcessStartTime(1, 0)
    setProcessServiceTime(1, 8)
    setProcessStartTime(2, 1)
    setProcessServiceTime(2, 1)
    setProcessStartTime(3, 4)
    setProcessServiceTime(3, 8)
    setProcessStartTime(4, 7)
    setProcessServiceTime(4, 5)
    var map: Map[String, Any] = Map()
    validateOptions()


  def randomThreeOne: Unit = //[(0,9),(2,6),(6,3)]
    resetProcessData
    setProcessStartTime(0, 0)
    setProcessServiceTime(0, 9)
    setProcessStartTime(1, 2)
    setProcessServiceTime(1, 6)
    setProcessStartTime(2, 6)
    setProcessServiceTime(2, 3)
    var map: Map[String, Any] = Map()
    validateOptions()

  def randomThreeTwo: Unit = //[(0,4),(4,7),(5,7)]
    resetProcessData
    setProcessStartTime(0, 0)
    setProcessServiceTime(0, 4)
    setProcessStartTime(1, 4)
    setProcessServiceTime(1, 7)
    setProcessStartTime(2, 5)
    setProcessServiceTime(2, 7)
    var map: Map[String, Any] = Map()
    validateOptions()


  def randomFiveOne: Unit = //[(0,2), (1,8), (5,4), (7,3), (9,1)]
    resetProcessData
    setProcessStartTime(0, 0)
    setProcessServiceTime(0, 2)
    setProcessStartTime(1, 1)
    setProcessServiceTime(1, 8)
    setProcessStartTime(2, 5)
    setProcessServiceTime(2, 4)
    setProcessStartTime(3, 7)
    setProcessServiceTime(3, 3)
    setProcessStartTime(4, 9)
    setProcessServiceTime(4, 1)
    var map: Map[String, Any] = Map()
    validateOptions()

  def randomFiveTwo: Unit = //[(0,6), (1,3), (2,2), (3,7), (4,1)]
    resetProcessData
    setProcessStartTime(0, 0)
    setProcessServiceTime(0, 6)
    setProcessStartTime(1, 1)
    setProcessServiceTime(1, 3)
    setProcessStartTime(2, 2)
    setProcessServiceTime(2, 2)
    setProcessStartTime(3, 3)
    setProcessServiceTime(3, 7)
    setProcessStartTime(4, 4)
    setProcessServiceTime(4, 1)
    var map: Map[String, Any] = Map()
    validateOptions()
