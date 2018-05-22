package Apps

import Views.IntroView
import javafx.application.Application
import javafx.stage.Stage
import Controllers.IntroController

class IntroApp extends Application {
  private val introController  =  IntroController
  private val introView: IntroView = new IntroView

  override def start(primaryStage: Stage): Unit = {
      val introView = new IntroView
          introView.start(primaryStage)
  }
}
