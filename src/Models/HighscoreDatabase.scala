package Models

import Models.BoardSize.BoardSize

import scala.collection.mutable._

object HighscoreDatabase {
  private val DATABASE_SIZE = 5
  private var highscoresMap: HashMap[BoardSize, Array[Highscore]] = new HashMap[BoardSize, Array[Highscore]](DATABASE_SIZE)

  def fetchHighscores: Array[Highscore] = highscores
  def updateHighscores(newHighscore: Highscore): Unit = {
    if (highscores.length < 5) {

    }
  }
}
