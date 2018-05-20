package Apps

import Controllers.IntroController
import Views.IntroView
import javafx.application.Application
import javafx.stage.Stage

class IntroApp extends Application {
  private val introController: IntroController = new IntroController
  private val introView: IntroView = new IntroView

  override def start(primaryStage: Stage): Unit = {
      //
  }
}
