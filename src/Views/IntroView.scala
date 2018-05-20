package Views

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.{GridPane, HBox, Priority}
import javafx.scene.text.Text
import javafx.stage.Stage
import Controllers.IntroController


object IntroView
{
  def main(args: Array[String])
  {
    Application.launch(classOf[IntroView], args: _*)
  }
}


class IntroView extends Application {


  def createButton(text :String, stage: Stage): HBox = {

    val controller = new IntroController
    val button = new Button
    button.setText(text)
    button.setId("Button")

    text match {

      case "PLAY" => button.setOnAction(controller.playBtnHandlerEvent(stage))
      case _ =>
    }


    val container = new HBox(button)
    container.setId("ButtonContainer")
    HBox.setHgrow(button, Priority.ALWAYS)


    container
  }

  def createText(inputText: String): HBox ={

    val text = new Text(inputText)
    text.setId("IntroText")

    val container = new HBox(text)
    container.setId("ButtonContainer")

    container
  }





  def generateIntroBox(stage: Stage): GridPane = {

    val gridPane = new GridPane



    gridPane.add(createText("KAKURO MY DEAR!"), 1, 0)
    gridPane.add(createButton("PLAY",stage), 1, 1)
    gridPane.add(createButton("SCORES",stage), 1, 2)
    gridPane.add(createButton("QUIT",stage), 1, 3)
    gridPane.add(createText(""), 1, 4)




    gridPane
  }


  def generateScene(gridPane: GridPane): Scene = {

    val scene = new Scene(gridPane, 400, 400)

    scene
  }


  override def start(primaryStage: Stage)
  {


    val root = generateIntroBox(primaryStage)
    val scene = generateScene(root)
    scene.getStylesheets.add("Views/styles/styles.css")
    primaryStage.setScene(scene)
    primaryStage.show()

  }

}