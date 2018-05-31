package Controllers

import Apps.IntroApp
import javafx.event.{ActionEvent, EventHandler}
import javafx.stage.Stage

object HighscoreController {
  def backButtonEventHandler(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        val introApp: IntroApp = new IntroApp
        introApp.start(stage)
      }
    }

    handler
  }

}
