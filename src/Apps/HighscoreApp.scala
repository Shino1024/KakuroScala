package Apps

import Controllers.HighscoreController
import Views.HighscoreView
import javafx.application.Application
import javafx.stage.Stage

class HighscoreApp extends Application {
  private val highscoreView = new HighscoreView

  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Kakuro highscores")

    val scene = highscoreView.generateScene
    scene.getStylesheets.add("Views/styles/styles.css")
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
