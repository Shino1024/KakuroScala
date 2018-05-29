package Apps

import Views.HighscoreView
import javafx.application.Application
import javafx.stage.Stage

class HighscoreApp extends Application {
  val highscoreView = new HighscoreView

  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Kakuro Highscores")

    val scene = highscoreView.generateScene()
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
