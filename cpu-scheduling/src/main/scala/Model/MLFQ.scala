package model

import scala.collection.mutable.Queue
import scala.collection.mutable.ArrayBuffer

class MLFQ extends Scheduler:
  override protected val name: String = "MLFQ"
  private var time = 0
  private var oneQueue: Queue[Process] = new Queue // ordered by start time
  private var twoQueue: Queue[Process] = new Queue

  override protected val algorithm =
    "When the currently running process ceases to execute, it is placed on the next lowest priority queue (if it hasn't completed its processing). The OS allocates the processor to the first process on the highest priority queue. The quantum is 2^i, where i is the priority of the queue on which the process had been waiting. MLFQ is a preemptive scheduling process."

  def increasingMagnitudes(scheduleLengths: ArrayBuffer[Int], serviceTime: Int): Boolean =
    var powerOfTwo = 1
    var cpu = 0
    var serviced = 0
    var increasingMagnitudes = true

    for s <- scheduleLengths do
      cpu = s
      serviced += s
      while cpu > 0 do
        cpu -= powerOfTwo
        powerOfTwo *= 2
      if cpu != 0 && serviced != serviceTime then
        increasingMagnitudes = false

    if serviced != serviceTime then
      increasingMagnitudes = false
    increasingMagnitudes

  private def allIncreasingMagnitudes(plan: StudentSolution): Boolean =
    var allRowsValid = true
    var scheduledLengths = ArrayBuffer[Int]()

    for processID <- 0 until Parameters.getNumProcesses do
      scheduledLengths = plan.processScheduledLengths(Parameters.getProcessName(processID))
      if !increasingMagnitudes(scheduledLengths, Parameters.getProcessServiceTime(processID)) then
        allRowsValid = false

    allRowsValid

  override def provideFeedback(plan: StudentSolution): String =
    val sb = new StringBuilder("")
    sb ++= super.prepareGenericFeedbackMessage(plan)

    if !allIncreasingMagnitudes(plan) then
      sb ++= prefix + "At least one process was not scheduled in lengths of increasing powers of 2." + postfix

    var returnValue = sb.toString()
    if returnValue.length > 0 then
      feedbackBlurb + returnValue
    else
      noFeedbackMessage

  /** A scheduler must be able to schedule the jobs in the job queue */
  def schedule: Unit =
    cpu.init
    for i <- 0 until jobQ.length do readyQ.enqueue(jobQ.dequeue())

    while readyQ.nonEmpty || oneQueue.nonEmpty || twoQueue.nonEmpty do
      if readyQ.isEmpty || readyQ.head.getStartTime > time then
        if oneQueue.isEmpty || oneQueue.head.getStartTime > time then
          if twoQueue.isEmpty || twoQueue.head.getStartTime > time then
            cpu.allocateCPU(idle)
          else
            val currentProcessTwo = twoQueue.dequeue()
            val unitsWorkedTwo = currentProcessTwo.work(4)
            for u <- 1 to unitsWorkedTwo do cpu.allocateCPU(currentProcessTwo)
            if currentProcessTwo.getProcessingTime > 0 then
              twoQueue.enqueue(currentProcessTwo)
            time += unitsWorkedTwo
        else
          val currentProcessOne = oneQueue.dequeue()
          val unitsWorked = currentProcessOne.work(2)
          for u <- 1 to unitsWorked do cpu.allocateCPU(currentProcessOne)
          if currentProcessOne.getProcessingTime > 0 then
            twoQueue.enqueue(currentProcessOne)
          time += unitsWorked
      else
        val currentProcess = readyQ.dequeue()
        currentProcess.work()
        cpu.allocateCPU(currentProcess)
        time += 1
        if currentProcess.getProcessingTime > 0 then
          oneQueue.enqueue(currentProcess)

//number one feedback - running something off q when a lesser q is not empty already
//running something for the wrong quanta (dictacted from a queue it did not come off of)
