package Controllers

import java.time.{LocalDate, LocalDateTime, LocalTime}
import java.time.temporal.ChronoUnit

import Apps.IntroApp
import Models._
import Util.Settings
import Views.{BoardQuit, Check, KakuroView, NewBoard}
import javafx.scene.input.MouseEvent
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Node
import javafx.scene.control.TextField
import javafx.scene.layout.{HBox, StackPane}
import javafx.scene.text.Text
import javafx.stage.Stage

import scala.util.Random
import scala.util.control.Breaks

class KakuroController extends GenericController {
  private val kakuroView = new KakuroView
  private var primaryStage: Stage = _

  private val kakuroBoard: KakuroBoard = new KakuroBoard(Settings.boardSize)
  private var sumBoard = new SumBoard()

  private var selectedCell: HBox = _

  private var startTime: LocalDateTime = LocalDateTime.now()
  private var endTime: LocalDateTime = _

  installBaseHandlers()

  kakuroView.injectSaveHighscoreButtonHandler(saveHighscoreButtonHandler)

  kakuroView.injectKakuroBoard(generateCellBoard())

  override def setStage(stage: Stage): Unit = {
    primaryStage = stage
  }

  override def showStage(): Unit = {
    val scene = kakuroView.generateScene
    val boardName = Settings.boardSize.toString.toLowerCase
    primaryStage.setTitle("Kakuro game: " + boardName + " board")

    scene.getStylesheets.add("Views/styles/styles.css")
    primaryStage.setScene(scene)
    primaryStage.show()
  }

  // BUTTON HANDLING
  def numberButtonHandler(text: String): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        changeSelectedCellText(text)
      }
    }

    handler
  }

  def quitButtonEventHandler(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        val introApp = new IntroApp
        introApp.start(stage)
      }
    }

    handler
  }

  def checkButtonEventHandler(_stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        kakuroView.disableInput()
        kakuroView.setFinishTime(getTime)
        kakuroView.displayWinBox()
        println(sumBoard.checkBoard())
      }
    }

    handler
  }

  def newBoardEventHandler(stage: Stage): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        startTimer()
        kakuroView.injectKakuroBoard(generateCellBoard())
        kakuroView.updateKakuroBoardView()
      }
    }

    handler
  }

  def installBaseHandlers(): Unit = {
    kakuroView.injectActionButtonHandler(BoardQuit, quitButtonEventHandler(primaryStage))
    kakuroView.injectActionButtonHandler(Check, checkButtonEventHandler(primaryStage))
    kakuroView.injectActionButtonHandler(NewBoard, newBoardEventHandler(primaryStage))

    kakuroView.injectNumberButtonHandler(numberButtonHandler)

    kakuroView.injectKeyButtonHandler(selectedCellHandler)
  }

  def saveHighscoreButtonHandler(nick: TextField, stackPane: StackPane): EventHandler[ActionEvent] = {
    val handler = new EventHandler[ActionEvent] {
      def handle(e: ActionEvent): Unit = {
        val newHighscore = Highscore(nick.getText, LocalDate.now, getTime)
        HighscoreDatabase.updateHighscores(newHighscore, Settings.boardSize)
        stackPane.getChildren.remove(stackPane.getChildren.size() - 1)

        installBaseHandlers()

        startTimer()
        kakuroView.injectKakuroBoard(generateCellBoard())
        kakuroView.updateKakuroBoardView()
      }
    }

    handler
  }

  private def startTimer(): Unit = {
    startTime = LocalDateTime.now()
  }

  private def endTimer(): Unit = {
    endTime = LocalDateTime.now()
  }

  def getTime: LocalTime = {
    endTimer()

    val hoursDifference: Int = ChronoUnit.HOURS.between(startTime, endTime).asInstanceOf[Int]
    val minutesDifference: Int = (ChronoUnit.MINUTES.between(startTime, endTime) % 60).asInstanceOf[Int]
    val secondsDifference: Int = (ChronoUnit.SECONDS.between(startTime, endTime) % 60).asInstanceOf[Int]

    LocalTime.of(hoursDifference, minutesDifference, secondsDifference)
  }

  //CELL HANDLING
  def selectedCellHandler(cell: HBox): EventHandler[MouseEvent] = {
    val handler = new EventHandler[MouseEvent] {
      def handle(e: MouseEvent): Unit = {
        changeCellSelection(cell)
      }
    }

    handler
  }

  private def changeCellSelection(cell: HBox): Unit = {
    if (selectedCell == null) {
      cell.setId("SelectedInputCell")
      selectedCell = cell
    } else {
      selectedCell.setId("InputCell")
      cell.setId("SelectedInputCell")
      selectedCell = cell
    }
  }

  private def changeSelectedCellText(text: String): Unit = {
    if (selectedCell != null) {
      val node: Node = selectedCell.getChildren.get(0)
      node match {
        case textNode: Text =>
          textNode.setText(text)
      }
    }
  }

  //BOARD GENERATORS
  def generateCellBoard(): KakuroBoard = {
    val logicBoard = generateLogicBoard(Settings.boardSize.id, Settings.boardSize.id)

    //TEMPORARY ARRAY ONLY FOR CHECKING THE COVER OF LOGIC BOARD
    val markedBoard = Array.ofDim[Int](Settings.boardSize.id, Settings.boardSize.id)


    //WE NEED TO MARK THE BOARD IN ORDER TO MAKE SURE EVERY CELL I COVERED BY DEFINED SUM,
    // AND THERE ARE NO MULTIPLE SUMS FOR ROW OR COLUMN
    for (i <- 0 until Settings.boardSize.id) {
      for (j <- 0 until Settings.boardSize.id) {
        markedBoard(i)(j) = 0
      }
    }

    // FIRSTLY WE NEED ALL INPUT CELLS TO MATCH THEM TO THE SUM LIST
    // IN THE NEXT STEP
    for (i <- 0 until Settings.boardSize.id) {
      for (j <- 0 until Settings.boardSize.id) {
        logicBoard(i)(j) match {
          case 0 =>
          case 1 => kakuroBoard.setMatrixCell(i, j, new KakuroInputCell(i, j))

        }
      }
    }

    //GENERATE PROPER AMOUNT OF HINT CELLS, GENERATE SUM CHECKING
    for (i <- 0 until Settings.boardSize.id) {
      for (j <- 0 until Settings.boardSize.id) {
        logicBoard(i)(j) match {
          case 0 =>
            val kakuroCell = new KakuroHintCell(0, 0)

            //UP DIRECTION
            if (i - 1 >= 0 && markedBoard(i - 1)(j) == 0 && logicBoard(i - 1)(j) == 1 && kakuroCell.getVValue == 0) {

              sumBoard.createNewList()

              val loop = new Breaks
              loop.breakable {
                for (k <- i to 0 by -1) {
                  if (logicBoard(k)(j) == 0 && k != i) {
                    loop.break
                  } else {
                    markedBoard(k)(j) = 1

                    if (k != i)
                      sumBoard.addMatrixInputCell(kakuroBoard.getMatrixCell(k, j))
                  }
                }
              }

              val sumValue = Random.nextInt(34) + 1
              kakuroCell.setVValue(sumValue)
              sumBoard.addMatrixSumCell(new KakuroSumCell(sumValue))
              sumBoard.addList()


            }

            //DOWN DIRECTION
            if (i + 1 < Settings.boardSize.id && markedBoard(i + 1)(j) == 0 && logicBoard(i + 1)(j) != 0 && kakuroCell.getHValue == 0) {

              sumBoard.createNewList()


              val loop = new Breaks
              loop.breakable {
                for (k <- i until Settings.boardSize.id) {
                  if (logicBoard(k)(j) == 0 && k != i) {
                    loop.break
                  } else {
                    markedBoard(k)(j) = 1

                    if (k != i)
                      sumBoard.addMatrixInputCell(kakuroBoard.getMatrixCell(k, j))

                  }
                }
              }

              val sumValue = Random.nextInt(34) + 1
              kakuroCell.setHValue(sumValue)
              sumBoard.addMatrixSumCell(new KakuroSumCell(sumValue))
              sumBoard.addList()
            }

            //RIGHT DIRECTION
            if (j + 1 < Settings.boardSize.id && markedBoard(i)(j + 1) == 0 && logicBoard(i)(j + 1) != 0 && kakuroCell.getHValue == 0) {

              sumBoard.createNewList()

              val loop = new Breaks
              loop.breakable {
                for (k <- j until Settings.boardSize.id) {
                  if (logicBoard(i)(k) == 0 && k != j) {
                    loop.break
                  } else {
                    markedBoard(i)(k) = 1

                    if (k != j)
                      sumBoard.addMatrixInputCell(kakuroBoard.getMatrixCell(i, k))

                  }
                }
              }

              val sumValue = Random.nextInt(34) + 1
              kakuroCell.setHValue(sumValue)
              sumBoard.addMatrixSumCell(new KakuroSumCell(sumValue))
              sumBoard.addList()
            }

            //LEFT DIRECTION
            if (j - 1 >= 0 && (markedBoard(i)(j - 1) == 0 && logicBoard(i)(j - 1) != 0) && kakuroCell.getVValue == 0) {

              sumBoard.createNewList()

              val loop = new Breaks
              loop.breakable {
                for (k <- j to 0 by -1) {
                  if (logicBoard(i)(k) == 0 && k != j) {
                    loop.break
                  } else {
                    markedBoard(i)(k) = 1

                    if (k != j)
                      sumBoard.addMatrixInputCell(kakuroBoard.getMatrixCell(i, k))

                  }
                }
              }

              val sumValue = Random.nextInt(34) + 1
              kakuroCell.setVValue(sumValue)
              sumBoard.addMatrixSumCell(new KakuroSumCell(sumValue))
              sumBoard.addList()
            }

            //WHEN BLACK CELL DON'T NEED TO HAVE NUMBER, BUT WE HAVE TO MARKED IT AS COVERED
            markedBoard(i)(j) = 1
            kakuroBoard.setMatrixCell(i, j, kakuroCell)

          case _ =>
        }
      }
    }

    for (i <- 0 until Settings.boardSize.id) {
      for (j <- 0 until Settings.boardSize.id) {
        print(markedBoard(i)(j) + " ")

      }
      print("\n")
    }
    sumBoard.showBoard()


    //WE HAVE TO CHECK WHETER BOARD IS FULLY MARKED OR NOT
    if(checkMarkedBoard(markedBoard))
       return kakuroBoard
    else
      sumBoard = new SumBoard
      generateCellBoard()
  }

  def generateLogicBoard(rowSize: Int, colSize: Int): Array[Array[Int]] = {
    val board = Array.ofDim[Int](rowSize, colSize)
    // NOT DECIDED CELL -> -1
    //BLACK 0
    //WHITE 1
    for (i <- 0 until rowSize) {
      for (j <- 0 until colSize) {
        board(i)(j) = -1
      }
    }

    //1. BORDERS
    //ROWS
    for (i <- 0 until colSize) {
      board(0)(i) = Random.nextInt(2) // range of {0,1}
      board(rowSize - 1)(colSize - i - 1) = board(0)(i)
      //2. ROW 2 AND N-1
      if (board(0)(i) == 1 && i != colSize - 1 && i != 0) {
        board(1)(i) = 1
        board(rowSize - 2)(colSize - i - 1) = 1
      }

    }

    //COLUMNS
    for (j <- 0 until rowSize) {
      board(j)(0) = Random.nextInt(2)
      board(rowSize - j - 1)(colSize - 1) = board(j)(0)
      // 2. COLUMN 2 AND N-1
      if (board(j)(0) == 1 && j != rowSize - 1 && j != 0) {
        board(j)(1) = 1
        board(rowSize - j - 1)(colSize - 2) = 1
      }
    }

    //3 CENTER
    for (i <- 1 until colSize / 2) {
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

    if (colSize % 2 == 1) board(rowSize / 2)(colSize / 2) = 1

    //4. RANDOMIZE THE REST
    for (i <- 1 until (rowSize / 2)) {
      for (j <- 1 until colSize) {
        if (board(i)(j) == -1) {
          board(i)(j) = (Random.nextFloat() + 0.8).toInt
          board(rowSize - i - 1)(colSize - j - 1) = board(i)(j)
        }
      }
    }

    // 5. RELEASE BOUNDED WHITE  OR BLACK CELLS
    for (i <- 1 to (rowSize / 2)) {
      for (j <- 1 until (colSize - 1)) {
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

    //CORNERS
    board(0)(Settings.boardSize.id - 1) = 0
    board(0)(0) = 0
    board(Settings.boardSize.id - 1)(Settings.boardSize.id - 1) = 0
    board(Settings.boardSize.id - 1)(0) = 0

    board
  }

  def checkMarkedBoard(markedBoard: Array[Array[Int]]): Boolean = {

    for (i <- 0 until Settings.boardSize.id) {
      for (j <- 0 until Settings.boardSize.id) {
        if (markedBoard(i)(j) == 0)
            return false
      }
    }

    true
  }

}