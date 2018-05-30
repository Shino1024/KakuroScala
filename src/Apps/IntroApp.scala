package Apps

import Controllers.IntroController
import Views.{IntroView, Play, Scores, GameQuit}
import javafx.application.Application
import javafx.stage.Stage

class IntroApp extends Application {
  private val introView = new IntroView

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("KAKURO MY DEAR!")

    introView.injectActionButtonHandler(Play, IntroController.playBtnHandlerEvent(primaryStage))
    introView.injectActionButtonHandler(Scores, IntroController.highscoreBtnHandlerEvent(primaryStage))
    introView.injectActionButtonHandler(GameQuit, IntroController.quitBtnHandlerEvent(primaryStage))

    val scene = introView.generateScene

    scene.getStylesheets.add("Views/styles/styles.css")
    primaryStage.setScene(scene)
    primaryStage.show()
  }

}
