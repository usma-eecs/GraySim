package view

import scala.swing._

class FeedbackDialog(view: View):

  def showFeedback(text: String) =
    Dialog.showMessage(
      view,
      text,
      title = "Feedback"
    )
