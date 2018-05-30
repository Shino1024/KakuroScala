package Views

import Models._
import javafx.scene.control.{Button, TextField}
import javafx.scene.layout.{GridPane, HBox, Priority, StackPane}
import javafx.scene.text.Text
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene

import scala.collection.mutable

class KakuroView extends GenericView {

  trait KakuroButton {
    def name: String
  }
  case object NewBoard extends KakuroButton {
    def name: String = "NEW BOARD"
  }
  case object Check extends KakuroButton {
    def name: String = "CHECK"
  }
  case object Quit extends KakuroButton {
    def name: String = "QUIT"
  }

  private var buttonHandlers = new mutable.HashMap[KakuroButton, EventHandler[ActionEvent]]()

  def injectButtonHandler(kakuroButton: KakuroButton, buttonEventHandler: EventHandler[ActionEvent]): Unit = {
    buttonHandlers.update(kakuroButton, buttonEventHandler)
  }

  def generateActionButton(kakuroButton: KakuroButton): HBox = {
    var buttonView = new Button()



    buttonView.setText(kakuroButton.name)
    buttonView.setId("Button")
    buttonHandlers.get(kakuroButton) match {
      case Some(handler) => buttonView.setOnAction(handler)
      case None => throw new Exception("The " + kakuroButton.name.toLowerCase + " handler hasn't been installed.")
    }



    kakuroButton match {
      case Quit =>
        buttonView.setText(kakuroButton.name)
        buttonView.setId("Button")
        buttonHandlers.get(Quit()) match {
          case Some(handler) => buttonView.setOnAction(handler)
          case None => throw new Exception("The quit handler hasn't been installed.")
        }

      case Check =>
        buttonView.setText(kakuroButton.name)
        buttonView.setId("Button")
        buttonView.setOnAction(KakuroController.checkBtnHandlerEvent())

      case NewBoard =>
        buttonView.setText(kakuroButton.name)
        buttonView.setId("Button")
        buttonView.setOnAction(KakuroController.newBoardBtnHandlerEvent())
    }

    val container = new HBox(buttonView)
    container.setId("ButtonContainer")
    HBox.setHgrow(buttonView, Priority.ALWAYS)

    container
  }


  private def generateKeyButton(i: Int) = {
    val button = new Button
    button.setText(i.toString)
    button.setId("Button")
    button.setOnAction(KakuroController.numberBtnHandler(button.getText))

    val container = new HBox(button)
    container.setId("ButtonContainer")
    HBox.setHgrow(button, Priority.ALWAYS)

    container
  }

  private def createEmptyContainer(): HBox = {
    val emptyContainer = new HBox
    emptyContainer.setId("ButtonContainer")

    emptyContainer
  }

  def createContainer(kakuroCell: KakuroCell): HBox = {
    kakuroCell match {

      case kakuroCell: KakuroHintCell =>

        val text = new Text
        text.setId("HintCellText")

        (kakuroCell.getVValue, kakuroCell.getHValue) match {

            case (0, 0) =>
              text.setText("")

            case (0, value) if value != 0 =>
              text.setText (" \n    " + value.toString + " ")

            case  (value, 0) if value != 0 =>
              text.setText (" " + value.toString + "\n ")

            case (value1, value2) =>
              text.setText (" " + value1.toString + "\n    " + value2.toString + " ")
        }

        val container = new HBox(text)
        container.setId("HintCell")

        container

      case _: KakuroInputCell =>

        val text = new Text
        text.setText("")
        text.setId("InputCellText")

        val container = new HBox(text)
        container.setId("InputCell")
        HBox.setHgrow(text, Priority.ALWAYS)
        container.setOnMouseClicked(KakuroController.selectedCellHandler(container))

        container

      case _: KakuroEmptyCell =>

        val textField = new TextField()

        val container = new HBox(textField)
        container.setId("EmptyCell")
        HBox.setHgrow(textField, Priority.ALWAYS)

        container

    }
  }

  def fillScene(cellBoard: KakuroBoard, rowSize: Int, colSize: Int): GridPane  = {

    val root = new GridPane

    for(i <- 0 until rowSize){
      for( j <- 0 until colSize){
        root.add(createContainer(cellBoard.getMatrixCell(i,j)), j + 1 , i + 1)
      }
    }

    for (i <- 0 to rowSize + 4) {
      root.add(createEmptyContainer(), 0, i )
      root.add(createEmptyContainer(), colSize + 1, i )
    }

    for(i <- 0 to colSize + 1) {
      root.add(createEmptyContainer(), i, 0 )
      root.add(createEmptyContainer(), i, rowSize + 4 )
    }

    for(i <- rowSize +1 to rowSize + 3) {
      for (j <- 1 to colSize + 1) {

        (i,j) match {

          case (row , 2) if row == rowSize + 1 => root.add(generateKeyButton(1), 2, row)
          case (row , 3) if row == rowSize + 1 => root.add(generateKeyButton(2), 3, row)
          case (row , 4) if row == rowSize + 1 => root.add(generateKeyButton(3), 4, row)
          case (row , 2) if row == rowSize + 2 => root.add(generateKeyButton(4), 2, row)
          case (row , 3) if row == rowSize + 2 => root.add(generateKeyButton(5), 3, row)
          case (row , 4) if row == rowSize + 2 => root.add(generateKeyButton(6), 4, row)
          case (row , 2) if row == rowSize + 3 => root.add(generateKeyButton(7), 2, row)
          case (row , 3) if row == rowSize + 3 => root.add(generateKeyButton(8), 3, row)
          case (row , 4) if row == rowSize + 3 => root.add(generateKeyButton(9), 4, row)

          case (row, col) if row == rowSize + 1 && col == 6 => root.add(generateActionButton(Check()), col, row, 2, 1)
          case (row, col) if row == rowSize + 2 && col == 6 => root.add(generateActionButton(NewBoard()), col, row, 2 ,1)
          case (row, col) if row == rowSize + 3 && col == 6 => root.add(generateActionButton(Quit()), col, row, 2 ,1)

          case (row, col) if (row == rowSize + 2 && col == 7) || (row == rowSize + 3 && col == 7) || (row == rowSize + 1 && col == 7) =>
          case( _, _) => root.add(createEmptyContainer(), j, i )

        }
      }
    }

    root
  }

  override def generateScene: Scene = {
    val root = fillScene()
    val scene = new Scene(root, 1, 1)

    scene
  }

}