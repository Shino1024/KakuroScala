package Controllers

import javafx.event.{ActionEvent, EventHandler}
import javafx.stage.Stage

import Views.KakuroView

class IntroController extends App {
  def playBtnHandlerEvent(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        val kakuroBoard: KakuroView = new KakuroView
        kakuroBoard.start(stage)
      }
    }

    handler
  }

  def highscoreBtnHandlerEvent(stage: Stage): EventHandler[ActionEvent] = {
    //
  }

  def quitBtnHandlerEvent(stage: Stage): EventHandler[ActionEvent] = {
    //
  }
}