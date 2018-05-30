package Models

import java.io._
import java.time.{LocalDate, LocalTime}

import Models.BoardSize.BoardSize
import Util.Settings

import scala.collection.{mutable => m}

object HighscoreDatabase {
  private val DATABASE_SIZE = 5
  private var highscoreMap: m.HashMap[BoardSize, List[Highscore]] = new m.HashMap[BoardSize, List[Highscore]]()

  def fetchHighscores(boardSize: BoardSize): List[Highscore] = {
    highscoreMap.get(boardSize) match {
      case Some(highscores) => highscores
      case None => List()
    }
  }

  // TODO: Refactor this _somehow_.
  def updateHighscores(newHighscore: Highscore, boardSize: BoardSize): Unit = {
    highscoreMap.get(boardSize) match {
      case Some(highscores) =>
        for ((highscore, i) <- highscores.zipWithIndex) {
          if (highscore.time.isAfter(newHighscore.time)) {
            val newHighscores = highscores.take(i) ++ List(highscore) ++ highscores.drop(i)
            if (newHighscores.length > DATABASE_SIZE) {
              highscoreMap.update(boardSize, newHighscores.drop(DATABASE_SIZE))
            } else {
              highscoreMap.update(boardSize, newHighscores)
            }
          }
        }

      case None =>
        highscoreMap.update(boardSize, List(newHighscore))
    }
  }

  def parseHighscores(): Unit = {
    val in = new ObjectInputStream(new FileInputStream(Settings.highscoreFilename))
    highscoreMap = in.readObject().asInstanceOf[m.HashMap[BoardSize, List[Highscore]]]

    in.close()
  }

  def saveHighscores(): Unit = {
    val out = new ObjectOutputStream(new FileOutputStream(Settings.highscoreFilename))
    highscoreMap.update(BoardSize.SMALL, List(
      Highscore(
      "lol",
      LocalDate.of(2017, 2, 3),
      LocalTime.of(0, 3, 4, 0)
    ), Highscore(
      "xd",
        LocalDate.of(2017, 2, 3),
        LocalTime.of(0, 3, 5, 0)
    )))

    highscoreMap.update(BoardSize.BIG, List(
    Highscore(
      "xd",
      LocalDate.of(2017, 2, 3),
      LocalTime.of(0, 3, 5, 0)
    ), Highscore(
      "xd",
        LocalDate.of(2017, 2, 3),
        LocalTime.of(0, 3, 8, 0)
    ), Highscore(
      "xd",
        LocalDate.of(2017, 2, 3),
        LocalTime.of(0, 3, 15, 0)
    ), Highscore(
      "xd",
        LocalDate.of(2017, 2, 3),
        LocalTime.of(0, 4, 5, 0)
    ), Highscore(
      "xd",
        LocalDate.of(2017, 2, 3),
        LocalTime.of(1, 3, 5, 0)
    )))

    out.writeObject(highscoreMap)

    out.close()
  }
}
