package Views

import Models._
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.{Button, TextField}
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.Text
import javafx.scene.text.Font

import Controllers.KakuroController

import scala.util.Random

class KakuroView extends App {

  def createEmptyContainer(): HBox = {
    val emptyContainer = new HBox
    emptyContainer.setId("ButtonContainer")

    emptyContainer
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

  def createContainer(kakuroCell: KakuroCell): HBox = {
    kakuroCell match {

      case kakuroCell: KakuroHintCell =>

        val text = new Text
        text.setId("HintCellText")
        text.setText(" " + kakuroCell.getVValue.toString  + "\n    " + kakuroCell.getHValue.toString + " ")

        val container = new HBox(text)
        container.setId("HintCell")
        HBox.setHgrow(text, Priority.ALWAYS)

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

    for (i <- 0 to rowSize + 2) {
      root.add(createEmptyContainer(), 0, i )
      root.add(createEmptyContainer(), colSize + 1, i )
    }

    for(i <- 0 to colSize + 1) {
      root.add(createEmptyContainer(), i, 0 )
      root.add(createEmptyContainer(), i, rowSize + 2 )
    }

    for(i <- 1 to 9) {
      root.add( generateKeyButton(i), i, rowSize + 1 )
    }

    root
  }

}