package Views

import java.time.{LocalDate, LocalTime}

import Models.BoardSize.BoardSize
import Models.{BoardSize, Highscore, HighscoreDatabase}
import Util.Settings
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.{TableColumn, TableView}
import javafx.scene.layout.GridPane
import javafx.scene.text.Text

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

class HighscoreView extends GenericView {
  var highscoreTables: List[TableView[Highscore]] = List()

  val highscoreColumnTitles: List[String] = List(
    "Name",
    "Date",
    "Time"
  )

  private def getHighscores(boardSize: BoardSize): List[Highscore] = {
    HighscoreDatabase.fetchHighscores(boardSize)
  }

  private def generateTable(boardSize: BoardSize): TableView[Highscore] = {
    var columns = new ListBuffer[TableColumn[Highscore, String]]()
    for (title <- highscoreColumnTitles) {
      val column = new TableColumn[Highscore, String](title)
      column.setCellValueFactory(new PropertyValueFactory(title.toLowerCase))
      columns += column
    }

    val highscoreTable = new TableView[Highscore]()
    highscoreTable.setId("HighscoreTable")
    for (column <- columns) {
      highscoreTable.getColumns.add(column)
    }

    val highscoreList = getHighscores(boardSize).asJava
    val observableHighscoresList = FXCollections.observableArrayList(highscoreList)
    highscoreTable.setItems(observableHighscoresList)
    observableHighscoresList.add(Highscore("nigga", LocalDate.of(2014, 5, 4), LocalTime.of(4, 2, 1, 0)))

    highscoreTable
  }

  def generateTables(): GridPane = {
    val tablesPane = new GridPane()
    tablesPane.setVgap(20)
    tablesPane.setPadding(new Insets(10, 10, 10, 10))

    val tableCaption = new Text("Highscores")
    tableCaption.setId("IntroText")
    tablesPane.add(tableCaption, 0, 0)

    for ((boardSize, i) <- BoardSize.values.zipWithIndex) {
      tablesPane.add(generateTable(boardSize), 0, i + 1)
    }

    tablesPane
  }

  override def generateScene: Scene = {
    val root = generateTables()
    val scene = new Scene(root, Settings.menuHeight, Settings.menuWidth)

    scene
  }
}
