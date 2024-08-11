package model

class FIFO(numberOfFrames: Int) extends Memory(numberOfFrames):
  override protected val name: String = "FIFO"
  override protected val incorrectColumnString = "Replace the page that has been in memory the longest in Column "

  override protected val algorithm = "Replace the page that was brought into memory first."

  def addPage(page: Int): Unit =
    var frameIndex = getFrameContainingPage(page)

    if frameIndex == -1 then
      frameIndex = getEmptyFrame()
      if frameIndex == -1 then
        frameIndex = selectPageToReplace()
      // Update the chosen frame with the new page
      frames(frameIndex).init // Clear the frame before adding a new page
      frames(frameIndex).addPagetoFrame(page)
      frames(frameIndex).entrytime = timeStep // Update entrytime

      // Update memory for all frames
      updateFrameMemory(page, frameIndex)
    else
      updateFrameMemory(page, -1)
    timeStep += 1

  def selectPageToReplace(): Int =
    frames.zipWithIndex.minBy(_._1.entrytime)._2 //utilizes entrytime

  override def provideFeedback(plan: StudentSolution) : String =
    val sb = new StringBuilder("")
    sb ++= super.prepareGenericFeedbackMessage(plan)

    val returnValue = sb.toString
    if returnValue.nonEmpty then
        feedbackBlurb + returnValue
    else
        noFeedbackMessage
