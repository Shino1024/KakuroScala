package Apps

import Controllers.KakuroController
import Views.KakuroView
import javafx.application.Application
import javafx.stage.Stage

class KakuroApp extends Application {
  private val introController: KakuroController = new KakuroController
  private val introView: KakuroView = new KakuroView

  override def start(primaryStage: Stage): Unit = {
    //
  }
}
