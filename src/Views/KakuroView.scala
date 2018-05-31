package Views

import ViewGenerator.generateButton
import Models._
import Util._
import javafx.scene.control.TextField
import javafx.scene.layout.{GridPane, HBox, Priority}
import javafx.scene.text.Text
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.input.MouseEvent

import scala.collection.mutable

class KakuroView extends GenericView {
  private val actionButtonHandlers = new mutable.HashMap[KakuroButton, EventHandler[ActionEvent]]()
  private var keyButtonHandler: HBox => EventHandler[MouseEvent] = _
  private var numberButtonHandler: String => EventHandler[ActionEvent] = _
  private var kakuroBoard: KakuroBoard = _

  def injectActionButtonHandler(kakuroButton: KakuroButton, buttonEventHandler: EventHandler[ActionEvent]): Unit = {
    actionButtonHandlers.update(kakuroButton, buttonEventHandler)
  }

  def injectKeyButtonHandler(buttonEventHandler: HBox => EventHandler[MouseEvent]): Unit = {
    keyButtonHandler = buttonEventHandler
  }

  def injectNumberButtonHandler(buttonEventHandler: String => EventHandler[ActionEvent]): Unit = {
    numberButtonHandler = buttonEventHandler
  }

  def injectKakuroBoard(_kakuroBoard: KakuroBoard): Unit = {
    kakuroBoard = _kakuroBoard
  }

  def generateActionButton(kakuroButton: KakuroButton): HBox = {
    actionButtonHandlers.get(kakuroButton) match {
      case Some(handler) => ViewGenerator.generateButton(kakuroButton.name, handler)
      case None => throw new NoSuchElementException("The " + kakuroButton.name.toLowerCase + " handler hasn't been installed.")
    }
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

            case (value, 0) if value != 0 =>
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
        container.setOnMouseClicked(keyButtonHandler(container))

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

    for (i <- 0 until rowSize){
      for ( j <- 0 until colSize){
        root.add(createContainer(cellBoard.getMatrixCell(i,j)), j + 1 , i + 1)
      }
    }

    for (i <- 0 to rowSize + 4) {
      root.add(createEmptyContainer(), 0, i )
      root.add(createEmptyContainer(), colSize + 1, i )
    }

    for (i <- 0 to colSize + 1) {
      root.add(createEmptyContainer(), i, 0 )
      root.add(createEmptyContainer(), i, rowSize + 4 )
    }

    for (i <- rowSize +1 to rowSize + 3) {
      for (j <- 1 to colSize + 1) {

        (i, j) match {
          case (row , 2) if row == rowSize + 1 => root.add(generateButton(1.toString, numberButtonHandler(1.toString)), 2, row)
          case (row , 3) if row == rowSize + 1 => root.add(generateButton(2.toString, numberButtonHandler(2.toString)), 3, row)
          case (row , 4) if row == rowSize + 1 => root.add(generateButton(3.toString, numberButtonHandler(3.toString)), 4, row)
          case (row , 2) if row == rowSize + 2 => root.add(generateButton(4.toString, numberButtonHandler(4.toString)), 2, row)
          case (row , 3) if row == rowSize + 2 => root.add(generateButton(5.toString, numberButtonHandler(5.toString)), 3, row)
          case (row , 4) if row == rowSize + 2 => root.add(generateButton(6.toString, numberButtonHandler(6.toString)), 4, row)
          case (row , 2) if row == rowSize + 3 => root.add(generateButton(7.toString, numberButtonHandler(7.toString)), 2, row)
          case (row , 3) if row == rowSize + 3 => root.add(generateButton(8.toString, numberButtonHandler(8.toString)), 3, row)
          case (row , 4) if row == rowSize + 3 => root.add(generateButton(9.toString, numberButtonHandler(9.toString)), 4, row)

          case (row, col) if row == rowSize + 1 && col == 6 => root.add(generateActionButton(Check), col, row, 2, 1)
          case (row, col) if row == rowSize + 2 && col == 6 => root.add(generateActionButton(NewBoard), col, row, 2 ,1)
          case (row, col) if row == rowSize + 3 && col == 6 => root.add(generateActionButton(BoardQuit), col, row, 2 ,1)

          case (row, col) if (row == rowSize + 2 && col == 7) || (row == rowSize + 3 && col == 7) || (row == rowSize + 1 && col == 7) =>
          case( _, _) => root.add(createEmptyContainer(), j, i )
        }
      }
    }

    root
  }

  override def generateScene: Scene = {
    val root = fillScene(kakuroBoard, Settings.boardSize.id, Settings.boardSize.id)
    root.setId("App")
    val scene = new Scene(root, 700, 700)

    scene
  }

}