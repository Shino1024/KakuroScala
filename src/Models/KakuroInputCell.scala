package Models

import java.util

import javafx.scene.layout.HBox

class KakuroInputCell(row_pos: Int, column_pos: Int) extends KakuroCell {
  private var numbers: Array[Boolean] = new Array[Boolean](10)
  val NUMS = 10

  //INITIAL VALUE - EMPTY CELL
  private var box:HBox = _
  private val row: Int = row_pos
  private val column: Int = column_pos


  def setBox(hbox: HBox):Unit = {
    box = hbox
  }
  def getBox:HBox  = box

  def getRow :Int = row
  def getColumn :Int = column


  def setNumber(number: Int): Unit = {
    if (number >= 0 && number < NUMS) {
      numbers(number) = true
    }
  }

  def unsetNumber(number: Int): Unit = {
    if (number >= 0 && number < NUMS) {
      numbers(number) = false
    }
  }

  def getNumbers: util.ArrayList[Int] = {
    var returnNums: util.ArrayList[Int] = new util.ArrayList[Int]()
    for (i <- 0 until NUMS) {
      if (numbers(i)) {
        returnNums.add(i)
      }
    }

    returnNums
  }

}
