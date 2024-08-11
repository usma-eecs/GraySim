package model

import scala.collection.mutable.Set

class Model(requestStreamChoice: Int):
  var pageRS = new PageRequestStream(requestStreamChoice)

  private val computedSolutions = Map[Policy, Memory](
    (Policy.OPT, new OPT(Parameters.getNumFrames)),
    (Policy.FIFO, new FIFO(Parameters.getNumFrames)),
    (Policy.LRU, new LRU(Parameters.getNumFrames)),
    (Policy.CLOCK, new Clock(Parameters.getNumFrames))
  )

  private val studentSolutions = Map[Policy, StudentSolution](
    (Policy.OPT, new StudentSolution(Parameters.getNumFrames, getNumPageRequests)),
    (Policy.FIFO, new StudentSolution(Parameters.getNumFrames, getNumPageRequests)),
    (Policy.LRU, new StudentSolution(Parameters.getNumFrames, getNumPageRequests)),
    (Policy.CLOCK, new StudentSolution(Parameters.getNumFrames, getNumPageRequests))
  )

  private val message = "Page Replacement Simulator"

  def getMessage = message

  def getStudentSolution(policy: Policy): StudentSolution =
    studentSolutions(policy)

  def getStudentSolutionData(policy: Policy): Array[Array[String]] =
    studentSolutions(policy).getData

  def setPageRequestStream(choice: Int): Unit =
    pageRS.setPageRequestStream(choice)

  def getPageRequestStream(): PageRequestStream =
    pageRS

  def getNumPageRequests: Int =
    pageRS.length

  def pageIn(policy: Policy, frame: Int, page: String): Unit =
    studentSolutions(policy).pageIn(frame, page)

  def undo(policy: Policy): Unit =
    val response = studentSolutions(policy).pop
    if response != "undone" then
      println("Pop-up message: " + response)

  def init =
    pageRS.init
    computedSolutions(Policy.OPT).addForeknowledge(pageRS.getPageRequestStream)
    for policy <- Policy.values do
      studentSolutions(policy).init
      computedSolutions(policy).init(pageRS.getPageRequestStream)

  def getSchedulingAlgorithm(policy: Policy): String =
    computedSolutions(policy).getAlgorithm

  def provideFeedback(policy: Policy, correctSolutions: Set[String]): String =
    correctSolutions -= policy.getShortName
    if correctSolutions.size > 0 then
      // Scala doesn't have a replaceLast so I am abusing reverse and replaceFirst
//      val confusionString = correctSolutions.mkString(", ").reverse.replaceFirst(",", ", or".reverse).reverse
      val confusionString = correctSolutions.mkString(" or ")
      "It looks like you may have confused " + policy.getShortName + " with " + confusionString
    else
      computedSolutions(policy).provideFeedback(studentSolutions(policy))


  def getComputedSolution(policy: Policy): Array[Array[String]] =
    computedSolutions(policy).show
  def getComputedMemory(policy: Policy): Memory =
    computedSolutions(policy)

  //trial
  def isSolutionCorrect(policy: Policy, studentSolution: Array[Array[String]]): Boolean =
    val correctSolution = getComputedSolution(policy) // Ensure this method exists and returns the correct solution in a comparable format
    //TESTING PURPOSES: PRINT COMPUTED SOLUTIONS
    //println("computer solution:")
    //correctSolution.foreach { row =>
    //  println(row.mkString("[", ", ", "]"))
    //}
    correctSolution.zip(studentSolution).forall {
      case (correctRow, studentRow) => correctRow.sameElements(studentRow)
    }

  def allCorrectSolutions(studentSolution: Array[Array[String]]): Set[String] =
    val correctSolutions = Set[String]()
    for p <- Policy.values do
      if isSolutionCorrect(p, studentSolution) then
        correctSolutions += p.getShortName
    correctSolutions
