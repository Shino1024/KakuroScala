package Views

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


class KakuroView extends Application
{

  def createContainer(i: Int): HBox ={


    i match {
      case 0 =>


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

      case 1 =>

        val textField = new TextField()
        textField.setPrefHeight(400)
        textField.setStyle("-fx-background-color: white")
        val container = new HBox(textField)
        container.setAlignment(Pos.CENTER)
        container.setPadding(new Insets(1))
        HBox.setHgrow(textField, Priority.ALWAYS)
        container.setStyle("-fx-background-color: black")

        container
      case -1 =>

        val textField = new TextField()
        val container = new HBox(textField)
        container.setAlignment(Pos.CENTER)
        container.setPadding(new Insets(1))
        HBox.setHgrow(textField, Priority.ALWAYS)
        container.setStyle("-fx-background-color: red")



        container


    }
  }

  def  generateLogicBoard(rowSize:Int, colSize:Int): Array[Array[Int]] = {

    val board =  Array.ofDim[Int](rowSize,colSize)

    // NOT DECIDED CELL -> -1
    //BLACK 0
    //WHITE 1

    for(i <- 0 until rowSize){
      for(j <- 0 until colSize){
        board(i)(j) = -1
      }
    }

    //1. BORDERS
    //ROWS
    for(i <- 0 until colSize) {
      board(0)(i) = Random.nextInt(2) // range of {0,1}
      board(rowSize - 1)(colSize - i - 1) = board(0)(i)
      //2. ROW 2 AND N-1
      if (board(0)(i) == 1 && i != colSize - 1 && i != 0) {
        board(1)(i) = 1
        board(rowSize - 2)(colSize - i - 1) = 1
      }

    }
    //COLUMNS
    for(j <- 0 until rowSize){

      board(j)(0) = Random.nextInt(2)
      board(rowSize - j - 1)(colSize - 1) = board(j)(0)
      // 2. COLUMN 2 AND N-1
      if (board(j)(0) == 1 && j != rowSize - 1 && j != 0) {
        board(j)(1) = 1
        board(rowSize - j - 1)(colSize - 2) = 1
      }
    }

    //3 CENTER
    for(i <- 1 until colSize/2)
      if(board(rowSize/2 )(i-1) == 1) {
        if(i == 1){
          board(rowSize / 2)(i) = 1
          board(rowSize / 2)(colSize - i - 1) = 1

        } else{
          board(rowSize / 2)(i) = 0
          board(rowSize / 2)(colSize - i - 1) = 0
        }
      }else{
        board(rowSize / 2 )(i) = 1
        board(rowSize / 2 )(colSize - i - 1) = 1
      }

    if(colSize % 2 == 1 ) board(rowSize/2 )(colSize/2) = 1


    //4. RANDOMIZE THE REST
    for(i <- 1 until (rowSize / 2)){
      for(j <- 1 until colSize) {
        if(board(i)(j) == -1) {
          board(i)(j) = (Random.nextFloat() + 0.8).toInt
          board(rowSize - i - 1)(colSize - j - 1) = board(i)(j)
        }
      }
    }


    // 5. RELEASE BOUNDED WHITE  OR BLACK CELLS
    for(i <- 1 to (rowSize / 2)){
      for(j <- 1 until (colSize - 1)) {
        if (board(i - 1)(j) == 0 &&
          board(i)(j - 1) == 0 &&
          board(i + 1)(j) == 0 &&
          board(i)(j + 1) == 0 &&
          board(i)(j) == 1) {

          board(i - 1)(j) = 1
          board(i)(j - 1) = 1
          board(i + 1)(j) = 1
          board(i)(j + 1) = 1

          board(rowSize - i - 2)(colSize - j - 1) = 1
          board(rowSize - i - 1)(colSize - j - 2) = 1
          board(rowSize - i)(colSize - j - 1) = 1
          board(rowSize - i - 1)(colSize - j) = 1
        }
      }
    }

    board

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

  def createEmptyContainer(): HBox = {

    val emptyContainer = new HBox
    emptyContainer.setId("ButtonContainer")

    emptyContainer
  }



  def fillScene( board: Array[Array[Int]], rowSize: Int, colSize: Int): GridPane  = {

    val root = new GridPane
    for(i <- 0 until rowSize){
      for( j <- 0 until colSize){
        root.add(createContainer(board(i)(j)), j + 1 , i + 1)
      }
    }


    for (i <- 0 to rowSize + 2){
      root.add(createEmptyContainer(), 0, i )
      root.add(createEmptyContainer(), colSize + 1, i )
    }

    for(i <- 0 to colSize + 1){

      root.add(createEmptyContainer(), i, 0 )
      root.add(createEmptyContainer(), i, rowSize + 2 )

    }

    for(i <- 1 to 9){
      root.add( generateKeyButton(i), i, rowSize + 1 )
    }

    root
  }



  def start(primaryStage: Stage)
  {

    primaryStage.setTitle("KAKURO MY DEAR!")

    val rowSize = 9
    val colSize = 9


    val logicBoard = generateLogicBoard(rowSize, colSize)
    val root = fillScene(logicBoard,rowSize,colSize)

    val scene = new Scene(root, 700, 600)
    scene.getStylesheets.add("Views/styles/styles.css")

    primaryStage.setScene(scene)
    primaryStage.show()

  }

}