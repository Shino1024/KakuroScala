package Models

import java.io.{File, FileInputStream}

import Models.BoardSize.BoardSize

import scala.collection.{mutable => m}
//import scala.util.Marshal

object HighscoreDatabase {
  private val DATABASE_SIZE = 5
  private var highscoreMap: m.HashMap[BoardSize, List[Highscore]] = new m.HashMap[BoardSize, List[Highscore]]()

  //def fetchHighscores(boardSize: BoardSize): List[Highscore] = {
  //  highscoreMap.get(boardSize)
  //}

  def updateHighscores(newHighscore: Highscore, boardSize: BoardSize): Unit = {
    highscoreMap.get(boardSize) match {
      case Some(highscores) =>
        for ((highscore, i) <- highscores.zipWithIndex) {
          if (highscore.time.after(newHighscore.time)) {
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
    val in = new FileInputStream("result.txt")
    val bytes = Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray
 //   highscoreMap = Marshal.load[highscoreMap.type](bytes)
//    val mapper = new ObjectMapper()
//    highscoreMap = mapper.readValue(new File("results.txt"), highscoreMap.)
  }

  def saveHighscores(): Unit = {
//    val mapper = new ObjectMapper()
//    mapper.writeValue(new File("results.json"), highscoreMap)
  }
}
