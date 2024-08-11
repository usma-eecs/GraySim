import model.Model
import controller.Controller
import view.View

import model.Parameters

object Demo:
  var pageRequestStreamChoice = -1

  val usage = """
    Usage: run --stream [n] --verbose
  """
  def main(args: Array[String]): Unit =
    val argList = args.toList

    def processArgs(list: List[String]): Unit =
      list match
        case "--stream" :: value :: tail =>
          pageRequestStreamChoice = value.toInt
          processArgs(tail)
        case "--verbose" :: tail =>
          Parameters.setVerbose
          processArgs(tail)
        case "--frames" :: value :: tail =>
          Parameters.setNumFrames(value.toInt)
        case option :: tail =>
          println("Unknown option " + option)
          println(usage)
          sys.exit(-1)
        case Nil => ()

    processArgs(argList)
    Parameters.validateOptions()

    val model = new Model(pageRequestStreamChoice)
    val view = new View
    val controller = new Controller(view, model)

    model.init
    controller.init
    view.init(controller)
