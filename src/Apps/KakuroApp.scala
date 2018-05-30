package Apps

import Controllers.KakuroController
import Models.Settings
import Views.KakuroView
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class KakuroApp extends Application {
  private val kakuroView = new KakuroView

  override def start(primaryStage: Stage) {
    val boardName = Settings.boardSize.toString.toLowerCase
    primaryStage.setTitle("Kakuro game: " + boardName + " board")

    val cellBoard = KakuroController.generateCellBoard()
    val root = kakuroView.fillScene(cellBoard, Settings.boardSize.id, Settings.boardSize.id)

    //val scene = new Scene(root, 700, 700)
    val scene = kakuroView.generateScene
    scene.getStylesheets.add("Views/styles/styles.css")

    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
