import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage

import Apps.IntroApp

object Main
{
  def main(args: Array[String])
  {
    Application.launch(classOf[IntroApp], args: _*)
  }
}

class Main extends Application
{
  override def start(primaryStage: Stage)
  {
    primaryStage.setTitle("Hello World!")
    val btn = new Button
    btn.setText("Say 'Hello World'")
    btn.setOnAction((e: ActionEvent) => {
      println("Hello World!")
    })

    val root = new StackPane
    root.getChildren.add(btn)
    primaryStage.setScene(new Scene(root, 300, 250))
    primaryStage.show
  }

}