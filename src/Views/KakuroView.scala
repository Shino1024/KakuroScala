package Views

import Models.{KakuroCell, KakuroEmptyCell, KakuroHintCell, KakuroInputCell}
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

import scala.util.Random
//
//object KakuroView
//{
//  def main(args: Array[String])
//  {
//    Application.launch(classOf[KakuroView], args: _*)
//  }
//}


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

    val container = new HBox(button)
    container.setId("ButtonContainer")
    HBox.setHgrow(button, Priority.ALWAYS)

    container
  }

  def createContainer(kakuroCell: KakuroCell): HBox = {
    kakuroCell match {
      case _: KakuroHintCell =>
        val text = new Text

        text.setFont(Font.font("Fira Code", 15))
        text.setFill(Color.WHITE)
        text.setText( " " + (Random.nextInt(34) + 9).toString  + "\n    " + (Random.nextInt(34) + 9).toString + " ")

        val container = new HBox(text)
        container.setPrefHeight(400)
        container.setAlignment(Pos.CENTER)
        container.setPadding(new Insets(1))
        container.setStyle("-fx-background-color: linear-gradient(to right bottom, #2f3441 50%, #212531 50%);")
        HBox.setHgrow(text, Priority.ALWAYS)

        container

      case _: KakuroInputCell =>
        val textField = new TextField()
        textField.setPrefHeight(400)
        textField.setStyle("-fx-background-color: white")

        val container = new HBox(textField)
        container.setAlignment(Pos.CENTER)
        container.setPadding(new Insets(1))
        HBox.setHgrow(textField, Priority.ALWAYS)
        container.setStyle("-fx-background-color: black")

        container

      case _: KakuroEmptyCell =>
        val textField = new TextField()
        val container = new HBox(textField)
        container.setAlignment(Pos.CENTER)
        container.setPadding(new Insets(1))
        HBox.setHgrow(textField, Priority.ALWAYS)
        container.setStyle("-fx-background-color: red")

        container

    }
  }

  def fillScene(board: Array[Array[Int]], rowSize: Int, colSize: Int): GridPane  = {
    val root = new GridPane
    for(i <- 0 until rowSize){
      for( j <- 0 until colSize){
        root.add(createContainer(board(i)(j)), j + 1 , i + 1)
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