package model

import view._

class Process(private val name: String,
              private val serviceTime: Int,
              private val startTime: Int):

  private var remainingServiceTime = serviceTime
  private var started = false

  def init =
    remainingServiceTime = serviceTime
    started = false

  def processOrder(p: Process) = p.startTime

  def getName = name
  def getStartTime = startTime
  def getServiceTime = serviceTime

  def getProcessingTime = remainingServiceTime

  def isStarted = started

  def start: Unit =
    started = true

  def work(unitsWorked: Int = 1): Int =
    var units = 0
    if remainingServiceTime < unitsWorked then
      units = remainingServiceTime
    else
      units = unitsWorked
    remainingServiceTime -= units
    units

  override def toString: String = s"$name($startTime,$serviceTime)"
