package Apps

import Models.{BoardSize, HighscoreDatabase}
import Views.HighscoreView
import javafx.application.Application
import javafx.stage.Stage

class HighscoreApp extends Application {
  private val highscoreView = new HighscoreView

  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("Kakuro highscores")

    HighscoreDatabase.saveHighscores()
    HighscoreDatabase.parseHighscores()
    val hd = HighscoreDatabase.fetchHighscores(BoardSize.SMALL)
    println(hd)

    val scene = highscoreView.generateScene

    scene.getStylesheets.add("Views/styles/styles.css")
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
