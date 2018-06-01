package Views

import java.time.LocalTime

import ViewGenerator.generateButton
import Models._
import Util._
import ViewGenerator._
import javafx.scene.control.TextField
import javafx.scene.layout.{GridPane, HBox, Priority, StackPane}
import javafx.scene.text.Text
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.input.MouseEvent

import scala.collection.mutable

class KakuroView extends GenericView {
  private val actionButtonHandlers = new mutable.HashMap[KakuroButton, EventHandler[ActionEvent]]()
  private var keyButtonHandler: HBox => EventHandler[MouseEvent] = _
  private var numberButtonHandler: String => EventHandler[ActionEvent] = _

  private var saveHighscoreButtonHandler: (TextField, StackPane) => EventHandler[ActionEvent] = _

  private var kakuroBoard: KakuroBoard = _

  private var finishTime: LocalTime = _

  private val root: StackPane = new StackPane

  private def dummyHandler[T <: javafx.event.Event]() = new EventHandler[T] {
    override def handle(event: T): Unit = {}
  }

  def injectActionButtonHandler(kakuroButton: KakuroButton, buttonEventHandler: EventHandler[ActionEvent]): Unit = {
    actionButtonHandlers.update(kakuroButton, buttonEventHandler)
  }

  def injectKeyButtonHandler(buttonEventHandler: HBox => EventHandler[MouseEvent]): Unit = {
    keyButtonHandler = buttonEventHandler
  }

  def injectNumberButtonHandler(buttonEventHandler: String => EventHandler[ActionEvent]): Unit = {
    numberButtonHandler = buttonEventHandler
  }

  def injectSaveHighscoreButtonHandler(buttonEventHandler: (TextField, StackPane) => EventHandler[ActionEvent]): Unit = {
    saveHighscoreButtonHandler = buttonEventHandler
  }

  def setFinishTime(time: LocalTime): Unit = {
    finishTime = time
  }

  def disableInput(): Unit = {
    for (actionType <- List(NewBoard, Check, BoardQuit)) {
      actionButtonHandlers.update(actionType, dummyHandler[ActionEvent]())
    }

    numberButtonHandler = { _: String =>
      dummyHandler[ActionEvent]()
    }

    keyButtonHandler = { _: HBox =>
      dummyHandler[MouseEvent]()
    }
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

  def updateKakuroBoardView(): Unit = {
    root.getChildren.set(0, makeKakuroBoard())
  }

  def makeKakuroBoard(): GridPane = {
    val root = new GridPane

    for (i <- 0 until kakuroBoard.size.id) {
      for (j <- 0 until kakuroBoard.size.id) {
        root.add(createContainer(kakuroBoard.getMatrixCell(i,j)), j + 1 , i + 1)
      }
    }

    root
  }

  def makeControlPanel(): GridPane = {
    val root = new GridPane

    val rowSize = kakuroBoard.size.id
    val colSize = kakuroBoard.size.id

    for (i <- 0 to 4) {
      root.add(createEmptyContainer(), 0, i )
      root.add(createEmptyContainer(), colSize + 1, i )
    }

    for (i <- 0 to colSize + 1) {
      root.add(createEmptyContainer(), i, 0 )
      root.add(createEmptyContainer(), i, 4 )
    }

    for (i <- 1 to 3) {
      for (j <- 1 to colSize + 1) {

        (i, j) match {
          case (row , 2) if row == 1 => root.add(generateButton(1.toString, numberButtonHandler(1.toString)), 2, row)
          case (row , 3) if row == 1 => root.add(generateButton(2.toString, numberButtonHandler(2.toString)), 3, row)
          case (row , 4) if row == 1 => root.add(generateButton(3.toString, numberButtonHandler(3.toString)), 4, row)
          case (row , 2) if row == 2 => root.add(generateButton(4.toString, numberButtonHandler(4.toString)), 2, row)
          case (row , 3) if row == 2 => root.add(generateButton(5.toString, numberButtonHandler(5.toString)), 3, row)
          case (row , 4) if row == 2 => root.add(generateButton(6.toString, numberButtonHandler(6.toString)), 4, row)
          case (row , 2) if row == 3 => root.add(generateButton(7.toString, numberButtonHandler(7.toString)), 2, row)
          case (row , 3) if row == 3 => root.add(generateButton(8.toString, numberButtonHandler(8.toString)), 3, row)
          case (row , 4) if row == 3 => root.add(generateButton(9.toString, numberButtonHandler(9.toString)), 4, row)

          case (row, col) if row == 1 && col == 6 => root.add(generateActionButton(Check), col, row, 2, 1)
          case (row, col) if row == 2 && col == 6 => root.add(generateActionButton(NewBoard), col, row, 2 ,1)
          case (row, col) if row == 3 && col == 6 => root.add(generateActionButton(BoardQuit), col, row, 2 ,1)

          case (row, col) if (row == 2 && col == 7) || (row == 3 && col == 7) || (row == 1 && col == 7) =>
          case( _, _) => root.add(createEmptyContainer(), j, i )
        }
      }
    }

    root
  }

//  def fillScene(cellBoard: KakuroBoard, rowSize: Int, colSize: Int): GridPane  = {
//    val root = new GridPane
//
//    for (i <- 0 until rowSize){
//      for ( j <- 0 until colSize){
//        root.add(createContainer(cellBoard.getMatrixCell(i,j)), j + 1 , i + 1)
//      }
//    }
//
//    for (i <- 0 to rowSize + 4) {
//      root.add(createEmptyContainer(), 0, i )
//      root.add(createEmptyContainer(), colSize + 1, i )
//    }
//
//    for (i <- 0 to colSize + 1) {
//      root.add(createEmptyContainer(), i, 0 )
//      root.add(createEmptyContainer(), i, rowSize + 4 )
//    }
//
//    for (i <- rowSize +1 to rowSize + 3) {
//      for (j <- 1 to colSize + 1) {
//
//        (i, j) match {
//          case (row , 2) if row == rowSize + 1 => root.add(generateButton(1.toString, numberButtonHandler(1.toString)), 2, row)
//          case (row , 3) if row == rowSize + 1 => root.add(generateButton(2.toString, numberButtonHandler(2.toString)), 3, row)
//          case (row , 4) if row == rowSize + 1 => root.add(generateButton(3.toString, numberButtonHandler(3.toString)), 4, row)
//          case (row , 2) if row == rowSize + 2 => root.add(generateButton(4.toString, numberButtonHandler(4.toString)), 2, row)
//          case (row , 3) if row == rowSize + 2 => root.add(generateButton(5.toString, numberButtonHandler(5.toString)), 3, row)
//          case (row , 4) if row == rowSize + 2 => root.add(generateButton(6.toString, numberButtonHandler(6.toString)), 4, row)
//          case (row , 2) if row == rowSize + 3 => root.add(generateButton(7.toString, numberButtonHandler(7.toString)), 2, row)
//          case (row , 3) if row == rowSize + 3 => root.add(generateButton(8.toString, numberButtonHandler(8.toString)), 3, row)
//          case (row , 4) if row == rowSize + 3 => root.add(generateButton(9.toString, numberButtonHandler(9.toString)), 4, row)
//
//          case (row, col) if row == rowSize + 1 && col == 6 => root.add(generateActionButton(Check), col, row, 2, 1)
//          case (row, col) if row == rowSize + 2 && col == 6 => root.add(generateActionButton(NewBoard), col, row, 2 ,1)
//          case (row, col) if row == rowSize + 3 && col == 6 => root.add(generateActionButton(BoardQuit), col, row, 2 ,1)
//
//          case (row, col) if (row == rowSize + 2 && col == 7) || (row == rowSize + 3 && col == 7) || (row == rowSize + 1 && col == 7) =>
//          case( _, _) => root.add(createEmptyContainer(), j, i )
//        }
//      }
//    }
//
//    root
//  }

  def generateWinBox(): GridPane = {
    val winPane = new GridPane()
    winPane.setId("WinPane")

    val winCaption = generateCaption("Congratulations!", MinorCaption)
    val timeCaption = generateCaption("Your time: ", TinyCaption)
    val timeValue = generateCaption(finishTime.toString, TinyCaption)

    val nick = new TextField
    nick.setPromptText("Your nick")

    val submitButton = generateButton("Submit", saveHighscoreButtonHandler(nick, root))

    winPane.add(winCaption, 0, 0, 2, 1)
    winPane.add(timeCaption, 0, 1)
    winPane.add(timeValue, 1, 1)
    winPane.add(nick, 0, 2)
    winPane.add(submitButton, 1, 2)

    winPane
  }

  def displayWinBox(): Unit = {
    root.getChildren.add(generateWinBox())
  }

  override def generateScene: Scene = {
    val gameScene = new GridPane
    gameScene.add(makeKakuroBoard(), 0, 0, kakuroBoard.size.id + 1, 1)
    gameScene.add(makeControlPanel(), 0, 1)

    root.getChildren.add(gameScene)

    root.setId("App")
    val scene = new Scene(root, 700, 700)

    scene
  }

}