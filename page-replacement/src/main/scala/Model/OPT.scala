package model

import scala.collection.mutable.ArrayBuffer
import scala.util.boundary, boundary.break

//class OPT(numberOfFrames: Int, oracle: ArrayBuffer[Int]=ArrayBuffer.empty[Int]) extends Memory(numberOfFrames) {
class OPT(numberOfFrames: Int) extends Memory(numberOfFrames):
  override protected val name: String = "OPT"
  override protected val incorrectColumnString = "Replace the page that will not be used again or will not be used for the longest time in Column "

  override protected val algorithm = "Replace the page that will be used furthest into the future."

  // A list to keep track of all page requests for future reference
  private var pageRequestStream: List[Int] = List()
  var oracle: ArrayBuffer[Int] = ArrayBuffer.empty[Int]

  override def addForeknowledge(pages: Array[Int]): Unit =
    for page <- pages do
      oracle.addOne(page)

  def addPageRequestStream(pages: List[Int]): Unit =
    pageRequestStream = pages
    pages.foreach(addPage)

  def addPage(page: Int): Unit =
    oracle.remove(0)

    var frameIndex = getFrameContainingPage(page)
    if frameIndex == -1 then
      frameIndex = getEmptyFrame()
      if frameIndex == -1 then
        frameIndex = selectPageToReplace()

      // Replace the page in the selected frame
      frames(frameIndex).init // Clear the frame before adding a new page
      frames(frameIndex).addPagetoFrame(page)
      frames(frameIndex).entrytime = timeStep // Update entry time

      // Update history for all frames
      updateFrameMemory(page, frameIndex)
    else
      // Page is already in a frame, just update histories without adding
      updateFrameMemory(page, -1) // -1 indicates no frame update needed

    // Increment time step
    timeStep += 1

  def selectPageToReplace(): Int =
    var optFrame = 0
    var latestAccess = -1
    var nextAccess = 0

    boundary:
      for p <- 0 until numberOfFrames do
        nextAccess = oracle.indexOf(frames(p).getCurrentPage())
        if nextAccess == -1 then
          optFrame = p
          break(0)
        if nextAccess > latestAccess then
          optFrame = p // save the frame number
          latestAccess = nextAccess

    optFrame
