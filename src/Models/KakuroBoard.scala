package Models

import Models.BoardSize.BoardSize

class KakuroBoard(val _size: BoardSize) {
  private val size: BoardSize = _size
  private var matrix: Array[KakuroCell] = new Array[KakuroCell](size.id * size.id)

  def setMatrixCell(row: Int, column: Int, kakuroCell: KakuroCell) = {
    matrix(row*size.id + column) = kakuroCell
  }

  def  getMatrixCell(row:Int, column:Int): KakuroCell = matrix(row*size.id + column)


  // /  def
}
