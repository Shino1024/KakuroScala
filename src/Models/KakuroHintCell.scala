package Models


class KakuroHintCell(val _vValue: Int, val _hValue: Int) extends KakuroCell {

  var vValue: Int = _vValue
  var hValue: Int = _hValue

  def getVValue: Int = vValue
  def getHValue: Int = hValue

  def setVValue(value:Int):Unit = {
    vValue = value
  }

  def setHValue(value:Int):Unit = {
    hValue = value
  }


}
