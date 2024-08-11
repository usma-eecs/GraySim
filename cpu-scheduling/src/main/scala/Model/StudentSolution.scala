package model

import scala.collection.mutable.ArrayBuffer
import model.Parameters

class StudentSolution:
  val schedulingPlan: Array[Array[Char]] = Array.ofDim[Char](Parameters.getNumProcesses, Parameters.getTotalServiceTime)
  val idleEntry = '-'
  init

  def init =
    /* Initialize the schedulingPlan to show all elements as unscheduled */
    for row <- 0 until Parameters.getNumProcesses do
      for column <- 0 until Parameters.getTotalServiceTime do
        schedulingPlan(row)(column) = idleEntry

  private def getRow(processId: String): Int =
    var process = 0
    var processRow = -1

    while process < Parameters.processNames.length-1 do
      if Parameters.processNames(process) == processId then
        processRow = process
      process += 1
    processRow

  def schedule(processId: String, timeUnit: Int) =
    var processRow = -1
    for row <- 0 until Parameters.processNames.length do
    if Parameters.processNames(row) == processId then
      processRow = row
    assert(processRow != -1)
    schedulingPlan(processRow)(timeUnit) = processId(0)

  def unschedule(processId: String, timeUnit: Int) =
    var processRow = -1
    for row <- 0 until Parameters.processNames.length do
      if Parameters.processNames(row) == processId then
        processRow = row
    assert(processRow != -1)
    schedulingPlan(processRow)(timeUnit) = idleEntry

  def isScheduled(r: Int, c: Int): Boolean =
    schedulingPlan(r)(c) != '-'

  def getEntry(row: Int, column: Int): Char =
    assert(row >= 0)
    assert(row < Parameters.getNumProcesses)
    assert(column >= 0)
    assert(column < Parameters.getTotalServiceTime)
    schedulingPlan(row)(column)

  def isEntrySelected(row: Int, column: Int): Boolean =
    assert(row >= 0)
    assert(row < Parameters.getNumProcesses)
    assert(column >= 0)
    assert(column < Parameters.getTotalServiceTime)
    schedulingPlan(row)(column) != '-'

  def processScheduledLengths(processId: String): ArrayBuffer[Int] =
    val processRow = getRow(processId)
    var scheduleLengths = ArrayBuffer[Int]()
    var length = 0

    for column <- 0 until Parameters.getTotalServiceTime do
      if schedulingPlan(processRow)(column) == processId(0) then
        length += 1
      else if length > 0 then
        scheduleLengths += length
        length = 0
    if length > 0 then
      scheduleLengths += length
    scheduleLengths

  def show: String =
    val sb = new StringBuilder("")
    for row <- 0 until Parameters.getNumProcesses do
      sb ++= Parameters.getProcessName(row) + ": "
      for column <- 0 until Parameters.getTotalServiceTime do
        sb ++= schedulingPlan(row)(column) + " "
      sb ++= "\n"
    sb.toString
