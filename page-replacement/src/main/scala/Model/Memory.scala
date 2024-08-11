package model

abstract class Memory(numberOfFrames: Int):
  protected val name: String = "Default"
  protected val algorithm = "The default page replacement algorithm is undefined."

  protected val prefix = "  - "
  protected val postfix = "\n"
  protected val feedbackBlurb = "Your solution is incorrect. Consider the following observations:\n"
  protected val noFeedbackMessage = "Your solution is incorrect. Unfortunately, we have no specific feedback for you. \n\nPlease send a screenshot of your solution to your instructor so that we can improve this tool."
  protected val incorrectColumnString = "Take a closer look at Column "

  // Initialize a history tracker for each frame without the initial "-"
  protected val frameMemory = Array.fill(numberOfFrames)(List.empty[String])

  def init(pages: Array[Int]): Unit =
    for page <- pages do
      addPage(page)

  def show: Array[Array[String]] = frameMemory.map(list => list.toArray)

  def addForeknowledge(pages: Array[Int]): Unit = ???

  def getAlgorithm: String = algorithm

  val frames = Array.fill(numberOfFrames)(new Frame)
  var timeStep = 0 // Tracks the number of page requests handled

  // Concrete classes must define how to select a page to replace
  def selectPageToReplace() : Int

  //how to create a generic memory, not specific to any algorithm

  def addPage(page: Int): Unit

  /* Find a frame that is empty and return that index; else return -1 */
  def getEmptyFrame(): Int = frames.indexWhere(_.isEmpty)

  /* Find the frame containing this page and return that index; else return -1 */
  def getFrameContainingPage(page: Int): Int =
    frames.indexWhere(_.containsPage(page))

  def getCurrentPage(f: Int): Int = frames(f).getCurrentPage()

  def updateFrameMemory(page: Int, updatedFrameIndex: Int): Unit =
    for i <- 0 until frames.length do
      val currentContent =
        if i == updatedFrameIndex then page.toString
        else frameMemory(i).lastOption.getOrElse("-")
      frameMemory(i) = frameMemory(i) :+ currentContent

  def showMemory(): String =
    val stateBuilder = new StringBuilder("Current memory state:\n")
    frameMemory.zipWithIndex.foreach { case (memory, index) =>
      val displayMemory = if memory.isEmpty then "-" else memory.mkString(", ")
      stateBuilder.append(s"Frame $index: $displayMemory\n")
    }
    stateBuilder.toString()

  def findFirstWrongColumn(plan:StudentSolution): Int =
    var colIndex = 0
    var numCorrect = 0
    val data = plan.getData

    while colIndex < timeStep  do
      numCorrect = 0
      for rowIndex <- 0 until numberOfFrames do
        if data(rowIndex)(colIndex) == frameMemory(rowIndex)(colIndex).toString() then
          numCorrect += 1
      if numCorrect == numberOfFrames then
        colIndex += 1
      else
        return colIndex
    -1

  def prepareGenericFeedbackMessage(plan: StudentSolution): String =
    val sb = new StringBuilder("")

    if !plan.complete then
      sb ++= prefix + "Your solution is incomplete" + postfix

    if plan.hasDuplicate then
      sb ++= prefix + "At least one page is resident in multiple frames" + postfix
      plan.show

    val firstIncorrect = findFirstWrongColumn(plan)
    if firstIncorrect != -1 then
      sb ++= prefix + incorrectColumnString + (firstIncorrect+66).toChar + postfix

    sb.toString()

  def provideFeedback(plan: StudentSolution): String =
    val genericFeedback = prepareGenericFeedbackMessage(plan)

    if genericFeedback.nonEmpty then
      feedbackBlurb + genericFeedback
    else
      noFeedbackMessage
