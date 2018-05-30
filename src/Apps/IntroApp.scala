package Apps

import Controllers.IntroController
import Views.IntroView
import javafx.application.Application
import javafx.stage.Stage

class IntroApp extends Application {
  private val introView = new IntroView

  override def start(primaryStage: Stage) {
    val scene = introView.generateScene

    scene.getStylesheets.add("Views/styles/styles.css")

    primaryStage.setTitle("KAKURO MY DEAR!")
    primaryStage.setScene(scene)
    primaryStage.show()
  }

}
