package Apps

import Controllers.IntroController
import Views.{IntroView, Play, Scores, GameQuit}
import javafx.application.Application
import javafx.stage.Stage

class IntroApp extends Application {
  private val introController = new IntroController

  override def start(primaryStage: Stage): Unit = {
    introController.setStage(primaryStage)
    introController.showStage()
  }

}
