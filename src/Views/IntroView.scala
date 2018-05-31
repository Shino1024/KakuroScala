package Views

import ViewGenerator.{generateButton, generateCaption}
import javafx.scene.Scene
import javafx.scene.layout.{GridPane, HBox}
import javafx.scene.text.Text
import javafx.event.{ActionEvent, EventHandler}

import scala.collection.mutable

class IntroView extends GenericView {
  private val actionButtonHandlers = new mutable.HashMap[IntroButton, EventHandler[ActionEvent]]()

  def injectActionButtonHandler(introButton: IntroButton, buttonEventHandler: EventHandler[ActionEvent]): Unit = {
    actionButtonHandlers.update(introButton, buttonEventHandler)
  }

  def generateActionButton(introButton: IntroButton): HBox = {
    actionButtonHandlers.get(introButton) match {
      case Some(handler) =>
        generateButton(introButton.name, handler)
      case None => throw new Exception("The " + introButton.name.toLowerCase + " handler hasn't been installed.")
    }
  }

  def createText(inputText: String): HBox = {
    val text = new Text(inputText)
    text.setId("IntroText")

    val container = new HBox(text)
    container.setId("ButtonContainer")

    container
  }

  def generateIntroLayout(): GridPane = {
    val gridPane = new GridPane

    gridPane.add(generateCaption("KAKURO MY DEAR!", TitleCaption), 0, 0)
    gridPane.add(generateActionButton(Play), 0, 1)
    gridPane.add(generateActionButton(Scores), 0, 2)
    gridPane.add(generateActionButton(GameQuit), 0, 3)
    gridPane.add(generateCaption("Made by Jarek & Mateusz", MinorCaption), 0, 4)

    gridPane
  }

  override def generateScene: Scene = {
    val root = generateIntroLayout()
    root.setId("App")
    val scene = new Scene(root)

    scene
  }

}