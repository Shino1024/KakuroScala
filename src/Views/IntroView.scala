package Views

import ButtonGenerator.generateButton

import javafx.scene.Scene
import javafx.scene.layout.{GridPane, HBox}
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.event.{ActionEvent, EventHandler}

import scala.collection.mutable

class IntroView extends GenericView {

  private val actionButtonHandlers = new mutable.HashMap[IntroButton, EventHandler[ActionEvent]]()

  def injectActionButtonHandler(introButton: IntroButton, buttonEventHandler: EventHandler[ActionEvent]): Unit = {
    actionButtonHandlers.update(introButton, buttonEventHandler)
  }

//  def setStage(_stage: Stage): Unit = {
//    if (stage == null) {
//      stage = _stage
//    }
//  }

//  def createButton(text: String, stage: Stage, handler: Stage => EventHandler[ActionEvent]): HBox = {
//    val button = new Button
//    button.setText(text)
//    button.setId("Button")
//    button.setOnAction(handler(stage))
//
//    val container = new HBox(button)
//    container.setId("ButtonContainer")
//    HBox.setHgrow(button, Priority.ALWAYS)
//
//    container
//  }

  def generateActionButton(introButton: IntroButton): HBox = {
    actionButtonHandlers.get(introButton) match {
      case Some(handler) =>
        println("AAAA")
        generateButton(introButton.name, handler)
      case None => throw new Exception("The " + introButton.name.toLowerCase + " handler hasn't been installed.")
    }
  }

  def createText(inputText: String): HBox ={
    val text = new Text(inputText)
    text.setId("IntroText")

    val container = new HBox(text)
    container.setId("ButtonContainer")

    container
  }

  def generateIntroLayout(): GridPane = {
    val gridPane = new GridPane

    gridPane.add(createText("KAKURO MY DEAR!"), 1, 0)
    gridPane.add(generateActionButton(Play), 1, 1)
    gridPane.add(generateActionButton(Scores), 1, 2)
    gridPane.add(generateActionButton(GameQuit), 1, 3)
//    gridPane.add(createButton("PLAY", stage, IntroController.playBtnHandlerEvent), 1, 1)
//    gridPane.add(createButton("SCORES", stage, IntroController.highscoreBtnHandlerEvent), 1, 2)
//    gridPane.add(createButton("QUIT", stage, IntroController.quitBtnHandlerEvent), 1, 3)
    gridPane.add(createText("Made by Jarek & Mateusz"), 1, 4)

    gridPane
  }

  override def generateScene: Scene = {
    val root = generateIntroLayout()
    val scene = new Scene(root, 400, 400)

    scene
  }

}