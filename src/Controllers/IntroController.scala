package Controllers

import Apps.{HighscoreApp, KakuroApp}
import javafx.event.{ActionEvent, EventHandler}
import javafx.stage.Stage

object IntroController {
  def playButtonEventHandler(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        val kakuroApp: KakuroApp = new KakuroApp
        kakuroApp.start(stage)
      }
    }

    handler
  }

  def highscoreButtonEventHandler(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        val highscoreApp: HighscoreApp = new HighscoreApp
        highscoreApp.start(stage)
      }
    }

    handler
  }

  def quitButtonEventHandler(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        Util.UtilFunctions.exitGame
      }
    }

    handler
  }

}