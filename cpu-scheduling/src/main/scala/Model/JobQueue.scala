package model

import scala.collection.mutable.PriorityQueue
import math.Ordering.comparatorToOrdering
import view._

/** The ProcessQueue keeps track of the order in which the processes are
  * scheduled to run.
  *
  * @constructor
  *   Create a new JobQueue.
  */
class JobQueue:
  private var jobQ: PriorityQueue[Process] =
    PriorityQueue()(using Parameters.getOrdering)

  init

  /** Initialize the process order by clearing the queue */
  def init =
    jobQ.clear()

  /** Tests whether the JobQueue is empty */
  def isEmpty(): Boolean =
    jobQ.isEmpty

  def nonEmpty(): Boolean =
    jobQ.nonEmpty

  /** Get start time of highest priority process in the queue */
  def nextStartTime(): Int =
    assert(jobQ.nonEmpty)
    val nextJob = jobQ.head
    nextJob.getStartTime

  def processOrder(p: Process) =
    p.getStartTime

  def addProcess(p: Process) =
    jobQ.enqueue(p)

  def addProcess(name: String, length: Int, start: Int) =
    var process = new Process(name, length, start)
    jobQ.enqueue(process)

  /** Show the current order in the Process Queue. */
  def show: String = "Job Queue: " + jobQ.mkString(", ")

  /** Return the number of Processes in the Queue. */
  def numberProcesses: Int =
    jobQ.length

  /** Dequeue the highest priority Process in the Queue. */
  def dequeue(): Process =
    jobQ.dequeue()

  def mkString(separator: String): String =
    jobQ.mkString(separator)

  /** Dequeue the highest priority Process, if its start time has passed. */
  def dequeue(time: Int): Option[Process] =
    var result: Option[Process] = None
    if this.nonEmpty() then
      if this.nextStartTime() <= time then result = Some(jobQ.dequeue())
    result

  def front(): Process =
    jobQ.head
