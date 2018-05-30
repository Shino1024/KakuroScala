package Apps

import Controllers.IntroController
import Views.{IntroView, Play, Scores, GameQuit}
import javafx.application.Application
import javafx.stage.Stage

class IntroApp extends Application {
  private val introView = new IntroView

  override def start(primaryStage: Stage) {
    primaryStage.setTitle("KAKURO MY DEAR!")

    introView.injectActionButtonHandler(Play, IntroController.playButtonEventHandler(primaryStage))
    introView.injectActionButtonHandler(Scores, IntroController.highscoreButtonEventHandler(primaryStage))
    introView.injectActionButtonHandler(GameQuit, IntroController.quitButtonEventHandler(primaryStage))

    val scene = introView.generateScene

    scene.getStylesheets.add("Views/styles/styles.css")
    primaryStage.setScene(scene)
    primaryStage.show()
  }

}
