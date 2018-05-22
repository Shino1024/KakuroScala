package Controllers

import Apps.KakuroApp
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

        printf("Highscores will be available soon ;)")
      }
    }

    handler  }

  def quitBtnHandlerEvent(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {

        printf("Quit will be available soon ;)")

      }
    }

    handler
  }

}