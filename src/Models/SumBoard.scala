package Models

import javafx.scene.Node
import javafx.scene.layout.HBox
import javafx.scene.text.Text


class SumBoard() {

  private var matrix: List[AuxiliarySumCell] = List()
  private var currentList: List[KakuroCell] = List()




  def createNewList():Unit = {
    currentList = List()
  }

  def addMatrixInputCell(cell: KakuroCell): Unit = {
    currentList = currentList :+ cell
  }

  def addMatrixSumList(value: Int): Unit = {

    var sumCell: AuxiliarySumCell = new AuxiliarySumCell(value, currentList)

    matrix = matrix :+ sumCell

  }




  def checkBoard(): Boolean = {

    for(sumCell: AuxiliarySumCell <- matrix){

      val requiredSum:Int = sumCell.getValue()
      var inputCellsSum:Int = 0

      val numbersArr = new Array[Boolean](10)
      for(i <- 1 to 9 ) numbersArr(i) = false

      for(element <- sumCell.getInputCellList()){

        element match {

          case cell:KakuroInputCell =>

            val node: Node = cell.getBox.getChildren.get(0)
            var currentValue : Int = 0

            node match {
              case textNode: Text =>
                if (textNode.getText != ""){

                  currentValue = textNode.getText.toInt

                  if(numbersArr(currentValue) == true) {
                    return false
                  }else{
                    numbersArr(currentValue) = true
                  }
                }else {
                  return false
                }
            }
           inputCellsSum = inputCellsSum + currentValue

          case _ => print("ERROR!")
        }
      }

      if (requiredSum != inputCellsSum) return false

      print(requiredSum + " " + inputCellsSum + "\n")
    }

      true
  }



  // WILL BE DELETED IN FINAL FORM OF THIS APP
  def showBoard():Unit = {


    for (sumCell: AuxiliarySumCell <- matrix) {

      print(sumCell.getValue() + " ")

      for (element <- sumCell.getInputCellList()) {

        element match {
          case element: KakuroInputCell =>
            print(element.getRow + " " + element.getColumn + ", ")

        }
      }
      print("\n")
    }

  }


}