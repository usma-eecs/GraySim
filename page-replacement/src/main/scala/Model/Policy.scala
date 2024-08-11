package model

enum Policy:
  case OPT, FIFO, LRU, CLOCK

  def getShortName: String =
    this match
      case OPT => "OPT"
      case FIFO => "FIFO"
      case LRU => "LRU"
      case CLOCK => "Clock"

  def getLongName =
    this match
      case OPT => "Optimal"
      case FIFO => "First In, First Out"
      case LRU => "Least Recently Used"
      case CLOCK => "Clock Algorithm"
