package model

import scala.collection.mutable.Queue

class RoundRobin extends Scheduler:
  override protected val name: String = "RoundRobin"
  private var time = 0
  private var rrQueue: Queue[Process] = new Queue //ordered by start time?
  private var quantum = 1

  override protected val algorithm =
    "When the currently running process ceases to execute or the timer expires, the process is moved back to the ready queue and the process that has been in the READY state the longest is selected to execute next for a time quanta."

  /** A scheduler must be able to schedule the jobs in the job queue */
  def schedule: Unit =
    quantum = Parameters.getQuantum
    cpu.init

    for i <- 0 until jobQ.length do readyQ.enqueue(jobQ.dequeue()) //all processes on here ordered by startTime
    /* Check for processes starting less than time */
    def processReadyToGo: Unit =
      while readyQ.nonEmpty && readyQ.head.getStartTime <= time do
        rrQueue.enqueue(readyQ.dequeue())

    processReadyToGo

    while readyQ.nonEmpty || rrQueue.nonEmpty do /*repeat until readyQ is empty*/
      //current process off of queue
      var currentProcess = rrQueue.dequeue()
      //current process run for quantum
      val unitsWorked = currentProcess.work(quantum)
      for u <- 1 to unitsWorked do cpu.allocateCPU(currentProcess)
      time += unitsWorked

      //check for processes ready to go
      processReadyToGo

      //enqueue current process if has more time to run
      if currentProcess.getProcessingTime > 0 then
        rrQueue.enqueue(currentProcess)


      //running for wrong quantum
      //running for longer than specified quantum
      //don't check readyQ before enqueuing currently running process
