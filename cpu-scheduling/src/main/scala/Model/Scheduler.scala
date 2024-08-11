package model

import scala.collection.mutable.PriorityQueue
import math.Ordering.comparatorToOrdering

abstract class Scheduler:
  protected val name: String = "Default"
  protected val algorithm = "The default scheduling algorithm is undefined."
  protected val idle = new Process("Idle", 0, 0)
  protected def ordering = Ordering.by[Process, Int](_.getStartTime).reverse
  //  WARNING: Do NOT use the ordering defined above in the declaration of jobQ
  //  protected var jobQ: PriorityQueue[Process] = PriorityQueue()(using ordering)
  protected var jobQ: PriorityQueue[Process] = PriorityQueue()(using Ordering.by[Process, Int](_.getStartTime).reverse)
  protected var readyQ: PriorityQueue[Process] = PriorityQueue()(using ordering)

  protected var cpu = new Trace
  protected val prefix = "  - "
  protected val postfix = "\n"
  protected val feedbackBlurb = "Your solution is incorrect. Consider the following observations:\n"
  protected val noFeedbackMessage = "Your solution is incorrect. Unfortunately, we have no specific feedback for you. \n\nPlease send a screenshot of your solution to your instructor so that we can improve this tool."

  /** Initializes the scheduler's data structures */
  def init =
    // For each process, add it to the jobQ.
    for processID <- 0 until Parameters.getNumProcesses do
      val process = new Process(Parameters.getProcessName(processID),
                                Parameters.getProcessServiceTime(processID),
                                Parameters.getProcessStartTime(processID))
      val processStartTime = Parameters.getProcessStartTime(processID)
      if processStartTime > 0 then
        jobQ.enqueue(process)
      if processStartTime == 0 then
        readyQ.enqueue(process)

  def getAlgorithm: String = algorithm

  /** A scheduler must be able to schedule the jobs in the job queue */
//  def schedule(jobs: JobQueue): Unit
//  For preemptive scheduling (e.g. round robin), the process currently running gets queued before newly arriving processes
  def schedule: Unit

  /** A scheduler must be able to produce its schedule */
  def showSchedule: String =
    cpu.show

  def showTimeSlice(timeSlice: Int): Char =
    cpu.showElement(timeSlice)

  /** A scheduler must be able to provide prepare feedback message(s) */
  def prepareGenericFeedbackMessage(plan: StudentSolution): String =
    val sb = new StringBuilder("")

    if doubleBooked(plan) then
      sb ++= prefix + "More than one process is scheduled in the same time slot." + postfix

    if processUnderAllocated(plan) then
      sb ++= prefix + "At least one process has been allocated too little time." + postfix

    if processOverAllocated(plan) then
      sb ++= prefix + "At least one process has been allocated too much time." + postfix

    if processesStartTooEarly(plan) then
      sb ++= prefix + "At least one process starts before its designated start time." + postfix

    sb.toString()

  /** Check if more than one process is scheduled at the same time */
  def doubleBooked(plan: StudentSolution): Boolean =
    var returnValue : Boolean = false
    for column <- 0 until Parameters.getTotalServiceTime do
      var count = 0
      for row <- 0 until Parameters.getNumProcesses do
        if plan.isScheduled(row, column) then count += 1
      if count > 1 then returnValue = true
    returnValue

  /** Processes should be scheduled for the proper amount of time */
  def checkProcessTimeAllocation(plan: StudentSolution): Boolean =
    var returnValue = true
    for row <- 0 until Parameters.getNumProcesses do
      var scheduledTime = 0
      for column <- 0 until Parameters.getTotalServiceTime do
        if plan.isScheduled(row, column) then
          scheduledTime += 1
      if scheduledTime != Parameters.getProcessServiceTime(row) then
        returnValue = false

    returnValue

  /** Process has been underallocated CPU time */
  def processUnderAllocated(plan: StudentSolution): Boolean =
    var returnValue = false
    for row <- 0 until Parameters.getNumProcesses do
      var scheduledTime = 0
      for column <- 0 until Parameters.getTotalServiceTime do
        if plan.isScheduled(row, column) then
          scheduledTime += 1
      if scheduledTime < Parameters.getProcessServiceTime(row) then
        returnValue = true

    returnValue

  /** Process has been overallocated CPU time */
  def processOverAllocated(plan: StudentSolution): Boolean =
    var returnValue = false
    for row <- 0 until Parameters.getNumProcesses do
      var scheduledTime = 0
      for column <- 0 until Parameters.getTotalServiceTime do
        if plan.isScheduled(row, column) then
          scheduledTime += 1
      if scheduledTime > Parameters.getProcessServiceTime(row) then
        returnValue = true

    returnValue

  /** Processes cannot begin before their start time */
  def processesStartTooEarly(plan: StudentSolution) : Boolean =
    var returnValue : Boolean = false
    for row <- 0 until Parameters.getNumProcesses do
      for column <- 0 until Parameters.getProcessStartTime(row) do
        if plan.isScheduled(row, column) then
          returnValue = true
    returnValue


  /** Turn a schedule into a trace */
  def createTrace(plan: StudentSolution): Trace =
    var studentTrace = new Trace
    var idle = "I"
    for column <- 0 until Parameters.getTotalServiceTime do
      var scheduled = false
      for row <- 0 until Parameters.getNumProcesses do
        if plan.isScheduled(row, column) then
          studentTrace.record(plan.getEntry(row, column).toString)
          scheduled = true
      if !scheduled then studentTrace.record(idle)
    studentTrace

  /** Determine if a student's scheduling plan is correct. */
  def isCorrect(plan: StudentSolution): Boolean =
    var returnValue = true
    if doubleBooked(plan) then
      returnValue = false
    else
      var studentTrace = createTrace(plan)
      for timeSlot <- 0 until Parameters.getTotalServiceTime do
        if studentTrace.showElement(timeSlot) != cpu.showElement(timeSlot) then
          returnValue = false
    returnValue

  /** Determine the number of correctly scheduled time slots */
  def checkAnswers(plan: StudentSolution) : Int =
    var totalCorrect = 0
    var studentTrace: Trace = createTrace(plan)

    for timeSlot <- 0 until Parameters.getTotalServiceTime do
      if studentTrace.showElement(timeSlot) == cpu.showElement(timeSlot) then
        totalCorrect += 1

    totalCorrect

  /** A scheduler must be able to provide basic feedback
   *  Assumption: The solution is incorrect. We won't be called if the solution passed the isCorrect check.
   */
  def provideFeedback(plan: StudentSolution): String =
    val genericFeedback = prepareGenericFeedbackMessage(plan)

    if genericFeedback.length > 0 then
      feedbackBlurb + genericFeedback
    else
      noFeedbackMessage
