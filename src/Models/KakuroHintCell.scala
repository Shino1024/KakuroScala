package Models


class KakuroHintCell(val _vValue: Int, val _hValue: Int) extends KakuroCell {

  val vValue: Int = _vValue
  val hValue: Int = _hValue

  def getVValue: Int = vValue
  def getHValue: Int = hValue

}
