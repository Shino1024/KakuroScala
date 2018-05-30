package Views

import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.control.Button
import javafx.scene.layout.{HBox, Priority}

object ButtonGenerator {
  def generateButton(text: String, eventHandler: EventHandler[ActionEvent]): HBox = {
    val buttonView = new Button()

    buttonView.setText(text)
    buttonView.setId("Button")
    buttonView.setOnAction(eventHandler)

    val container = new HBox(buttonView)
    container.setId("ButtonContainer")
    HBox.setHgrow(buttonView, Priority.ALWAYS)

    container
  }
}
