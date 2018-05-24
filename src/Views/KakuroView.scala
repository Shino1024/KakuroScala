package Views

import Models._
import javafx.scene.control.{Button, TextField}
import javafx.scene.layout.{GridPane, HBox, Priority, StackPane}

import javafx.scene.text.Text
import Controllers.KakuroController


class KakuroView extends App {


  def generateOptionButton(name: String):HBox = {

    val button = new Button

    name match {

      case "QUIT" =>

        button.setText(name)
        button.setId("Button")
        button.setOnAction(KakuroController.quitBtnHandlerEvent())

      case "CHECK" =>

        button.setText(name)
        button.setId("Button")
        button.setOnAction(KakuroController.checkBtnHandlerEvent())

      case "NEW BOARD" =>

        button.setText(name)
        button.setId("Button")
        button.setOnAction(KakuroController.newBoardBtnHandlerEvent())
    }

    val container = new HBox (button)
    container.setId ("ButtonContainer")
    HBox.setHgrow (button, Priority.ALWAYS)

    container
  }


  def generateKeyButton(i: Int) = {
    val button = new Button
    button.setText(i.toString)
    button.setId("Button")
    button.setOnAction(KakuroController.numberBtnHandler(button.getText))

    val container = new HBox(button)
    container.setId("ButtonContainer")
    HBox.setHgrow(button, Priority.ALWAYS)


    container
  }


  def createEmptyContainer(): HBox = {
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

          case (row, col) if row == rowSize + 1 && col == 6 => root.add(generateOptionButton("CHECK"), col, row, 2, 1)
          case (row, col) if row == rowSize + 2 && col == 6 => root.add(generateOptionButton("NEW BOARD"), col, row, 2 ,1)
          case (row, col) if row == rowSize + 3 && col == 6 => root.add(generateOptionButton("QUIT"), col, row, 2 ,1)

          case (row, col) if (row == rowSize + 2 && col == 7) || (row == rowSize + 3 && col == 7) || (row == rowSize + 1 && col == 7) =>
          case( _, _) => root.add(createEmptyContainer(), j, i )

        }
      }
    }

    root
  }

}