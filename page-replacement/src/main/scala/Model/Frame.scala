package model

import scala.collection.mutable.ArrayBuffer

// The Frame class will keep track of the pages within each frame
class Frame:

    var history: ArrayBuffer[Int] = ArrayBuffer.empty[Int]

    var entrytime: Int = 0 // FIFO
    var used: Int = 0 // LRU
    var usedbit: Int = 0 // optimal

    var frameNum: Int = 0
    init // !!!not needed!!!

    def init: Unit = history.clear()

    def isEmpty: Boolean = history.isEmpty

    def addPagetoFrame(page: Int): Unit = history += page

    def containsPage(page: Int): Boolean =
        if !history.isEmpty then
            history.last == page
        else
            false

    // N.B. If the history ArrayBuffer doesn't have enough pages in it, this will trigger an exception
    def getPage(frameNum: Int): Int = history(frameNum)

    // N.B. If history has no frames, this will trigger an exception
    def getCurrentPage(): Int = history.last

    def removeFirstPage(): Unit = if history.nonEmpty then history.remove(0)

    /** Show the frame status with the pages */
    def show: String =
        // "Frame " + frameNum + ": " + history.mkString(", ")
        s"Frame $frameNum: ${history.mkString(", ")}"

    def mkString(separator: String): String =
        history.mkString(separator)
