package model

class LRU(numberOfFrames: Int) extends Memory(numberOfFrames):
  override protected val name: String = "LRU"
  override protected val incorrectColumnString = "Replace the page that was accessed least recently in Column "

  override protected val algorithm = "Replace the page that has not been used for the longest time period."

  // Map that tracks the last access time for each page currently in frames
  private var lastAccessTimes: Map[Int, Int] = Map()

  def addPage(page: Int): Unit =
    // Update access time for the page
    lastAccessTimes = lastAccessTimes.updated(page, timeStep)

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
      // Page is already in a frame
      updateFrameMemory(page, -1) // -1 means no frame update needed

    timeStep += 1

  def selectPageToReplace(): Int =
    // Find the least recently used frame by comparing last access times of pages in frames
    val lruPage = frames.flatMap(_.history).minBy(lastAccessTimes)
    getFrameContainingPage(lruPage)

