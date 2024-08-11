package model

import scala.collection.mutable.ArrayBuffer

class Trace:
  private val maxTraceLength = Parameters.getTotalServiceTime
  private var cpuSchedule = ArrayBuffer.empty[String]

  init

  def init =
    cpuSchedule.clear()
    cpuSchedule.sizeHint(maxTraceLength)

  def allocateCPU(p: Process, units: Int = 1) =
    for i <- 1 to units do
      cpuSchedule.addOne(p.getName)

  def record(p: String) = cpuSchedule.addOne(p)

  def show: String = "Trace: " + cpuSchedule.mkString(", ")

  def showElement(i: Int): Char =
    assert(i < cpuSchedule.length)
    cpuSchedule(i)(0)
