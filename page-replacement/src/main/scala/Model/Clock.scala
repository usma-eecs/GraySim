package model

import scala.collection.mutable.Map

class Clock(numberOfFrames: Int) extends Memory(numberOfFrames):
  override protected val name: String = "Clock"
  override protected val algorithm = "Replace the page identified by the clock algorithm."

  // Map that tracks the last access time for each page currently in frames
  private val useBit: Map[Int, Boolean] = Map()
  private var clockPointer: Int = 0

  def addPage(page: Int): Unit =
    var frameIndex = getFrameContainingPage(page)

    if frameIndex == -1 then
      frameIndex = getEmptyFrame()
      if frameIndex == -1 then
        frameIndex = selectPageToReplace()

      // Replace the LRU page with the new page
      frames(frameIndex).init // Clear the frame before adding a new page
      frames(frameIndex).addPagetoFrame(page)
      frames(frameIndex).entrytime = timeStep // Update entry time
      updateFrameMemory(page, frameIndex)
    else
      updateFrameMemory(page, -1)

    setUseBit(frameIndex)

    timeStep += 1

  def selectPageToReplace(): Int =
    var frameIndex = -1
    while frameIndex == -1 do
      if useBit(clockPointer) then
        unsetUseBit(clockPointer)
      else
        frameIndex = clockPointer
      clockPointer += 1
      if clockPointer >= numberOfFrames then
        clockPointer = 0
    frameIndex

  private def setUseBit(frameIndex: Int) =
    useBit(frameIndex) = true

  private def unsetUseBit(frameIndex: Int) =
    useBit(frameIndex) = false
