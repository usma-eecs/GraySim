package model

//import scala.collection.mutable.Array
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/** The PageRequestStream keeps track of the order in which the pages are
  * requested.
  *
  * @constructor
  *   Create a new PageRequest.
  */
class PageRequestStream(choice: Int):
  var initd = false
  private val pageRequestStreams = Array(
    Array(2, 3, 1, 3, 4, 3, 5, 4, 3, 2, 1, 3, 6, 3),
    Array(3, 5, 2, 4, 3, 5, 4, 5, 2, 3, 5, 4, 2),
    Array(1, 2, 3, 2, 1, 4, 2, 5, 6),
    Array(1, 2, 3, 2, 3, 4),
    Array(2, 3, 1, 3, 4, 3, 5, 4),
    Array(1, 5, 3, 2, 4, 3, 5, 2, 1, 3, 2, 4, 3, 3)
  )
  private val pageRS: ArrayBuffer[Int] = ArrayBuffer()
  private var pageRequestStream:Array[Int] = Array()
  setPageRequestStream(choice)
  init

  /** Initialize the page request stream by clearing the array */
  def init =
    pageRS.clear()
    for page <- pageRequestStream do
      addPageRequest(page)

  def setPageRequestStream(choice: Int): Unit =
    var requestedStream = choice
    if requestedStream < 0 || requestedStream >= pageRequestStreams.length then
      requestedStream = Random.between(0, pageRequestStreams.length)
    pageRequestStream = pageRequestStreams(requestedStream)
    initd = true

  def clear = pageRS.clear()

  def getPageRequestStream: Array[Int] = pageRS.toArray

  /** Tests whether the page request stream is empty */
  def isEmpty: Boolean = pageRS.isEmpty

  def length =
    if !initd then
      init
    pageRS.length

  def index(i: Int) =
    if i >= pageRS.length then
      -1
    else
      pageRS(i)

  def nonEmpty(): Boolean = pageRS.nonEmpty

  def addPageRequest(page: Int) = pageRS += page

  /** Show the current Page Request String. */
  def show: String = "Page Request Stream: " + pageRS.mkString(", ")

  /** Return the number of Processes in the Queue. */
  def numberPageRequests: Int = pageRS.length

  def mkString(separator: String): String = pageRS.mkString(separator)
