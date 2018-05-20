package Models

import Models.BoardSize.BoardSize

class KakuroBoard(val _size: BoardSize) {
  private val size: BoardSize = _size
  private var matrix: Array[KakuroCell] = new Array[KakuroCell](size.id * size.id)

//  def
}
