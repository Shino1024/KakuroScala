package Models

class AuxiliarySumCell(val input_value: Int, val input_list: List[KakuroCell]) {

  private val value = input_value
  private val inputCellList = input_list


  def getValue():Int = value
  def getInputCellList(): List[KakuroCell] = inputCellList

}
