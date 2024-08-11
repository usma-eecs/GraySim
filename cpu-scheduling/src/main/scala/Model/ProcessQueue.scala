package model

import scala.collection.mutable.PriorityQueue
import math.Ordering.comparatorToOrdering
import view._

/** The ProcessQueue keeps track of the order in which the processes are
  * scheduled to run.
  *
  * @constructor
  *   Create a new ProcessQueue.
  */
class ProcessQueue:
  private var _view: Option[View] = None
  private var processQ: PriorityQueue[Process] =
    PriorityQueue()(using Parameters.getProcessQueueOrdering)
  init

  /** Initialize the process order by clearing the queue */
  def init: Unit = processQ.clear

  def addProcess(process: Process): Unit = processQ.enqueue(process)
  def removeProcess(): Process = processQ.dequeue()

  def isEmpty(): Boolean = processQ.isEmpty
  def nonEmpty(): Boolean = processQ.nonEmpty

  /** Show the current order in the Process Queue. */
  def show: String = "Process Queue: " + processQ.mkString(", ")

  /** Advance the Processes in the Queue, putting the one who had been first in
    * line to the end of the line.
    */
  // def advance: Process =
  //   val currentProcess = processQ.dequeue()
  //   processQ.enqueue(currentProcess)
  //   currentProcess

  /** Return the Process who is at the head of the Queue
    */
  def head: Process = processQ.head

  /** Return the number of Processes in the Queue. */
  def numberProcesses: Int = processQ.length
