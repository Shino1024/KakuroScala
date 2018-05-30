package Controllers

import Apps.{HighscoreApp, KakuroApp}
import javafx.application.Platform
import javafx.event.{ActionEvent, EventHandler}
import javafx.stage.Stage

object IntroController {
  def playBtnHandlerEvent(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        val kakuroApp: KakuroApp = new KakuroApp
        kakuroApp.start(stage)
      }
    }

    handler
  }

  def highscoreBtnHandlerEvent(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        val highscoreApp: HighscoreApp = new HighscoreApp
        highscoreApp.start(stage)
      }
    }

    handler
  }

  def quitBtnHandlerEvent(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        Platform.exit()
        System.exit(0)
      }
    }

    handler
  }

}