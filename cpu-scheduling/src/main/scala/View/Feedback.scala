package view

import scala.swing._
//import java.awt.Color

class FeedbackDialog(view: View):

  def showFeedback(text: String) =
    Dialog.showMessage(
      view,
      text,
      title = "Feedback"
    )
