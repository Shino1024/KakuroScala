package Models

import java.util

class KakuroInputCell extends KakuroCell {
  private var numbers: Array[Boolean] = new Array[Boolean](10)
  val NUMS = 10

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
