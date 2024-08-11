package model

enum Policy:
  case FIFO, SJF, STCF, RR, MLFQ

  def getShortName: String =
    this match
      case FIFO => "FIFO"
      case SJF => "SJF"
      case STCF => "STCF"
      case RR => "RR"
      case MLFQ => "MLFQ"

  def getLongName =
    this match
      case FIFO => "First In, First Out"
      case SJF => "Shortest Job First"
      case STCF => "Shortest Time-to-Completion First"
      case RR => "Round Robin"
      case MLFQ => "Multi-Level Feedback Queue"
