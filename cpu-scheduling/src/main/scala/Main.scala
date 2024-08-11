import model.Model
import model.Parameters
import model.Policy
import controller.Controller
import view.View
import scala.collection.mutable.Map

object Demo:

  val usage = """
    Usage: run --quantum <n> --p <starttime> <servicetime> ...
  """

  def main(args: Array[String]): Unit =
    var processNum = 0

    def readArgs(list: List[String]): Unit =
      list match
        case "--quantum" :: value :: tail =>
          Parameters.setQuantum(value.toInt)
          readArgs(tail)
        case "--p" :: startTime :: serviceTime :: tail =>
          Parameters.setProcessStartTime(processNum, startTime.toInt)
          Parameters.setProcessServiceTime(processNum, serviceTime.toInt)
          // until further notice, something must start at 0 - exit
          processNum += 1
          if processNum >= Parameters.maxProcesses then
            println("Too many processes")
            sys.exit(-1)
          readArgs(tail)
        case "--policy" :: policyName :: tail =>
          Parameters.setPolicy(
            policyName.toUpperCase() match
              case "FIFO" => Policy.FIFO
              case "SJF" => Policy.SJF
              case "STCF" => Policy.STCF
              case "RR" => Policy.RR
              case "MLFQ" => Policy.MLFQ
              case _ =>
                println(s"Unknown policy $policyName.")
                sys.exit(-1)
          )
        case option :: tail =>
          println(s"Unknown option $option")
          println(usage)
          sys.exit(-1)
        case Nil => ()

    readArgs(args.toList)

    if (!Parameters.validateOptions()) then {
      println("The process parameters given are invalid.")
      sys.exit(-1)
    }

    val model = new Model
    val view = new View
    val controller = new Controller(view, model)

    model.init
    controller.init
    view.init(controller)
