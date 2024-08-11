package model

class Model:
  private val computedSolutions = Map[Policy, Scheduler](
    (Policy.FIFO, new FIFO),
    (Policy.SJF, new SJF),
    (Policy.STCF, new STCF),
    (Policy.RR, new RoundRobin),
    (Policy.MLFQ, new MLFQ)
  )

  private val studentSolutions = Map[Policy, StudentSolution](
    (Policy.FIFO, new StudentSolution),
    (Policy.SJF, new StudentSolution),
    (Policy.STCF, new StudentSolution),
    (Policy.RR, new StudentSolution),
    (Policy.MLFQ, new StudentSolution)
  )

  private var processQueue = new ProcessQueue()

  private val message = "Scheduler Simulator"

  def getMessage = message

  def getNumProcesses = processQueue.numberProcesses

  def getStudentSolution(policy: Policy): StudentSolution =
    studentSolutions(policy)

  def init =
    processQueue.init
    var totalServiceTime = 0
    for j <- 0 until Parameters.getNumProcesses do
      totalServiceTime += Parameters.processServiceTimes(j)
    Parameters.setTotalServiceTime(totalServiceTime)
    for policy <- Policy.values do
      computedSolutions(policy).init
      computedSolutions(policy).schedule
      studentSolutions(policy).init

  def setStudentScheduleEntry(policy: Policy, process: String, timeSlice: Int) =
    studentSolutions(policy).schedule(process, timeSlice)

  def unsetStudentScheduleEntry(policy: Policy, process: String, timeSlice: Int) =
    studentSolutions(policy).unschedule(process, timeSlice)

  def getProcessScheduled(policy: Policy, timeSlice: Int): Char =
    computedSolutions(policy).showTimeSlice(timeSlice)

  def getStudentSolutionEntry(policy: Policy, row: Int, column: Int): Char =
    studentSolutions(policy).getEntry(row, column)

  def isStudentSolutionEntrySelected(policy: Policy, row: Int, column: Int): Boolean =
    studentSolutions(policy).isEntrySelected(row, column)

  def getSchedulingAlgorithm(policy: Policy): String =
    computedSolutions(policy).getAlgorithm

  def provideFeedback(policy: Policy): String =
    computedSolutions(policy).provideFeedback(studentSolutions(policy))

  def isCorrectSolution(policy: Policy): Boolean =
    computedSolutions(policy).isCorrect(studentSolutions(policy))

  def checkAnswers(policy: Policy): Int =
    computedSolutions(policy).checkAnswers(studentSolutions(policy))
