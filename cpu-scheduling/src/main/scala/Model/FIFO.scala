package model

class FIFO extends Scheduler:
  override protected val name: String = "FIFO"
  private var time = 0
  override protected val algorithm = "When the currently running process ceases to execute, the process that has been in the READY state the longest is selected to execute next."

  /** A scheduler must be able to schedule the jobs in the job queue */
  def schedule: Unit =
    cpu.init
    // FIFO works off a single queue so go ahead and put all the jobs in the jobsQ onto the readyQ
    for i <- 0 until jobQ.length do
      readyQ.enqueue(jobQ.dequeue())

    while readyQ.nonEmpty do
      var j = readyQ.dequeue()
      if j.getStartTime > time then
        for i <- time+1 to j.getStartTime do
          cpu.allocateCPU(idle)
          time += 1

      for t <- 1 to j.getProcessingTime do
        cpu.allocateCPU(j)
        time += 1

  /** process execution is segmented */
  def processPreempted(plan: StudentSolution): Boolean =
    var returnValue : Boolean = false
    for row <- 0 until Parameters.getNumProcesses do
      var processStarted = false
      var processCompleted = false

      for column <- 0 to Parameters.getTotalServiceTime-1 do
        if plan.isScheduled(row, column) then
          if processCompleted then
            returnValue = true
          else
            processStarted = true
        else
          if processStarted then
            processCompleted = true
    returnValue

  /** A scheduler must be able to provide specific feedback */
  override def provideFeedback(plan: StudentSolution) : String =
    val sb = new StringBuilder("")

    sb ++= super.prepareGenericFeedbackMessage(plan)

    if processPreempted(plan) then
      sb ++= prefix + "At least one process was preempted, but FIFO is non-preemptive." + postfix

    val returnValue = sb.toString
    if returnValue.length > 0 then
      feedbackBlurb + returnValue
    else
      noFeedbackMessage
