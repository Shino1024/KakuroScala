package Models

import java.util.Date

import Models.BoardSize.BoardSize

case class Highscore(name: String, date: Date, time: Date, boardSize: BoardSize)
