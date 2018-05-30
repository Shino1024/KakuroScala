package Controllers

import Apps.{HighscoreApp, KakuroApp}
import javafx.application.Platform
import javafx.event.{ActionEvent, EventHandler}
import javafx.stage.Stage

object IntroController {
  def playButtonEventHandler(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        println("playBtnHandlerEvent")
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
        Platform.exit()
        System.exit(0)
      }
    }

    handler
  }

}