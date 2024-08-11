package model

class SJF extends Scheduler:
  override protected val name: String = "SJF"
  override protected def ordering = Ordering
          .by[Process, (Int, Int)](p => (p.getServiceTime, p.getStartTime))
          .reverse
  private var time = 0
  override protected val algorithm =
    "When the currently running process ceases to execute, the process that has the SHORTEST expected processing time is selected next. SJF is a non-preemptive scheduling algorithm."

  /** A scheduler must be able to schedule the jobs in the job queue */
  def schedule: Unit =
    cpu.init

    while jobQ.nonEmpty do
      val j = jobQ.dequeue()
      while time < j.getStartTime do
        if readyQ.nonEmpty then
          val currentProcess = readyQ.dequeue()
          for t <- 1 to currentProcess.getProcessingTime do
            cpu.allocateCPU(currentProcess)
            time += 1
        else
          cpu.allocateCPU(idle)
          time += 1
      readyQ.enqueue(j)

    while readyQ.nonEmpty do
      val currentProcess = readyQ.dequeue()
      for t <- 1 to currentProcess.getProcessingTime do
        cpu.allocateCPU(currentProcess)
        time += 1


//run something longer when something shorter is available (didn't check readyQ)

//non-preemptive

  /** process execution is segmented */
  def processPreempted(plan: StudentSolution): Boolean =
    var returnValue : Boolean = false
    for row <- 0 until Parameters.getNumProcesses do
      var processStarted = false
      var processCompleted = false

      for column <- 0 until Parameters.getTotalServiceTime do
        if plan.isScheduled(row, column) then
            if processCompleted then
              returnValue = true
            else
              processStarted = true
        else if processStarted then
          processCompleted = true

    returnValue

    /** A scheduler must be able to provide specific feedback */
  override def provideFeedback(plan: StudentSolution) : String =
    val sb = new StringBuilder("")

    sb ++= super.prepareGenericFeedbackMessage(plan)

    if processPreempted(plan) then
        sb ++= prefix + "At least one process was preempted, but SJF is non-preemptive." + postfix

    val returnValue = sb.toString
    if returnValue.nonEmpty then
      feedbackBlurb + returnValue
    else
      noFeedbackMessage
