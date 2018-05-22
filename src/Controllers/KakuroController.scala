package Controllers

import Models._
import javafx.scene.input.MouseEvent
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Node
import javafx.scene.layout.HBox
import javafx.scene.text.Text

import scala.util.Random


object KakuroController {

  private val kakuroBoard: KakuroBoard = new KakuroBoard(Settings.boardSize)

  private var selectedCell: HBox = _


  def selectedCellHandler(cell: HBox): EventHandler[MouseEvent] = {

    val handler = new EventHandler[MouseEvent] {

      def handle(e: MouseEvent): Unit = {

        changeCellSelection(cell)

      }
    }

    handler
  }


  def numberBtnHandler(text: String): EventHandler[ActionEvent] = {

    val handler = new EventHandler[ActionEvent] {

      def handle(e: ActionEvent): Unit = {

        changeSelectedCellText(text)

      }
    }

    handler
  }


  private def changeCellSelection(cell: HBox):Unit = {

    if(selectedCell == null){

      cell.setId("SelectedInputCell")
      selectedCell = cell

    } else {

      selectedCell.setId("InputCell")
      cell.setId("SelectedInputCell")
      selectedCell = cell

    }
  }


  private def changeSelectedCellText(text: String): Unit = {

    if(selectedCell != null) {

      val node: Node = selectedCell.getChildren.get(0)

      if(node.isInstanceOf[Text]){

        node.asInstanceOf[Text].setText(text)

      }
    }
  }


  def   generateCellBoard(): KakuroBoard = {

    val logicboard = generateLogicBoard(Settings.boardSize.id, Settings.boardSize.id)

    for(i <- 0 until Settings.boardSize.id) {
      for (j <- 0 until Settings.boardSize.id) {

        logicboard(i)(j) match {

          case 1 => kakuroBoard.setMatrixCell(i, j, new KakuroInputCell)
          case 0 => kakuroBoard.setMatrixCell(i, j, new KakuroHintCell(Random.nextInt(34) + 9, Random.nextInt(34) + 9))
          case _ => print("error value :/")
        }
      }
    }

    kakuroBoard
  }


  def generateLogicBoard(rowSize:Int, colSize:Int): Array[Array[Int]] = {

    val board = Array.ofDim[Int](rowSize,colSize)

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
    for (i <- 1 until colSize/2) {
      if (board(rowSize / 2)(i - 1) == 1) {
        if (i == 1) {
          board(rowSize / 2)(i) = 1
          board(rowSize / 2)(colSize - i - 1) = 1

        } else {
          board(rowSize / 2)(i) = 0
          board(rowSize / 2)(colSize - i - 1) = 0
        }
      } else {
        board(rowSize / 2)(i) = 1
        board(rowSize / 2)(colSize - i - 1) = 1
      }
    }

    if (colSize % 2 == 1) board(rowSize/2)(colSize/2) = 1


    //4. RANDOMIZE THE REST
    for (i <- 1 until (rowSize / 2)){
      for (j <- 1 until colSize) {
        if (board(i)(j) == -1) {
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


}