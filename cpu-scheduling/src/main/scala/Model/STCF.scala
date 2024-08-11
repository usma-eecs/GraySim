package model

class STCF extends Scheduler:
  override protected val name: String = "STCF"
  override protected def ordering = Ordering
          .by[Process, (Int, Int)](p => (p.getProcessingTime, p.getStartTime))
          .reverse
  private var time = 0
  override protected val algorithm =
    "When the currently running process ceases to execute, the process that has the SHORTEST remaining processing time is selected next. STCF is a preemptive scheduling algorithm."

  /** A scheduler must be able to schedule the jobs in the job queue */
  def schedule: Unit =
    cpu.init

    while jobQ.nonEmpty do
      val j = jobQ.dequeue()
      while time < j.getStartTime do
        if readyQ.nonEmpty then
          val currentProcess = readyQ.dequeue()
          currentProcess.work()
          cpu.allocateCPU(currentProcess)
          time += 1
          if currentProcess.getProcessingTime > 0 then
            readyQ.enqueue(currentProcess)
        else
          cpu.allocateCPU(idle)
          time += 1
      readyQ.enqueue(j)
    while readyQ.nonEmpty do
      val currentProcess = readyQ.dequeue()
      currentProcess.work()
      cpu.allocateCPU(currentProcess)
      time += 1
      if currentProcess.getProcessingTime > 0 then
        readyQ.enqueue(currentProcess)


//run a process to completion
//didn't consider amount of remaining time as opposed to original time (came in with 8 but now only has 1)
