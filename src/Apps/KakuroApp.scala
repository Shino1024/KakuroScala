package Apps

import Controllers.KakuroController
import Util._
import Views.{Check, BoardQuit, KakuroView, NewBoard}
import javafx.application.Application
import javafx.stage.Stage

class KakuroApp extends Application {
  private val kakuroView = new KakuroView

  override def start(primaryStage: Stage) {
    val boardName = Settings.boardSize.toString.toLowerCase
    primaryStage.setTitle("Kakuro game: " + boardName + " board")

    kakuroView.injectActionButtonHandler(BoardQuit, KakuroController.quitButtonEventHandler(primaryStage))
    kakuroView.injectActionButtonHandler(Check, KakuroController.checkButtonEventHandler(primaryStage))
    kakuroView.injectActionButtonHandler(NewBoard, KakuroController.newBoardEventHandler(primaryStage))

    kakuroView.injectNumberButtonHandler(KakuroController.numberButtonHandler)

    kakuroView.injectKeyButtonHandler(KakuroController.selectedCellHandler)

    kakuroView.injectKakuroBoard(KakuroController.generateCellBoard())

    val scene = kakuroView.generateScene

    scene.getStylesheets.add("Views/styles/styles.css")
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
