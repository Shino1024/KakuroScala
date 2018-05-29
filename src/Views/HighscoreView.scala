package Views

import Models.BoardSize.BoardSize
import Models.{BoardSize, Highscore, HighscoreDatabase, Settings}
import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.{TableColumn, TableView}
import javafx.scene.layout.GridPane
import javafx.scene.text.Text

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class HighscoreView {
  var highscoreTables: List[TableView[Highscore]] = List()

  val highscoreColumnTitles: List[String] = List(
    "Name",
    "Date",
    "Time"
  )

  HighscoreDatabase.parseHighscores()

  private def getHighscores(boardSize: BoardSize): List[Highscore] = {
    HighscoreDatabase.fetchHighscores(boardSize)
  }

  private def generateTable(boardSize: BoardSize): TableView[Highscore] = {
    var columns = new ListBuffer[TableColumn[Highscore, String]]()
    for (title <- highscoreColumnTitles) {
      columns += new TableColumn[Highscore, String](title)
    }

    val highscoreTable = new TableView[Highscore]()
    highscoreTable.setId("HighscoreTable")
    for (column <- columns) {
      highscoreTable.getColumns.add(column)
    }

    val highscoreList = getHighscores(boardSize).asJava
    val observableHighscoresList = FXCollections.observableArrayList(highscoreList)
    highscoreTable.setItems(observableHighscoresList)

    highscoreTable
  }

  def generateTables(): GridPane = {
    var tablesPane = new GridPane()
    tablesPane.setHgap(20)
    val tableCaption = new Text("Highscores")
    tablesPane.add(tableCaption, 0, 1)
    for ((boardSize, i) <- BoardSize.values.zipWithIndex) {
      tablesPane.add(generateTable(boardSize), i + 1, 0)
    }

    tablesPane
  }

  // TODO: Make a generic View with abstract generateScene method.
  def generateScene(): Scene = {
    val root = generateTables()
    new Scene(root, Settings.menuHeight, Settings.menuWidth)
  }
}