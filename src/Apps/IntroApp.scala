package Apps

import Controllers.IntroController
import Views.{IntroView, Play, Scores, GameQuit}
import javafx.application.Application
import javafx.stage.Stage

class IntroApp extends Application {
  private val introView = new IntroView

  override def start(primaryStage: Stage) {
  }

}
