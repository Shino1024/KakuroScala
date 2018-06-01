package Models

import javafx.scene.Node
import javafx.scene.layout.HBox
import javafx.scene.text.Text


class SumBoard() {

  private var matrix: List[List[KakuroCell]] = List()
  private var currentList: List[KakuroCell] = List()


  def addList(): Unit = {
    matrix  = matrix :+ currentList
  }

  def createNewList():Unit = {
    currentList = List()
  }

  def addMatrixInputCell(cell: KakuroCell): Unit = {
    currentList = currentList :+ cell
  }

  def addMatrixSumCell(cell: KakuroCell): Unit = {

    currentList = cell :: currentList

  }




  def checkBoard(): Boolean = {

    for(list <- matrix){

      var requiredSum:Int = 0
      var inputCellsSum:Int = 0

      val numbersArr = new Array[Boolean](10)
      for(i <- 1 to 9 ) numbersArr(i) = false

      for(element <- list){

        element match {
          case cell:KakuroSumCell =>
            requiredSum = cell.getSum

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

    for(list <- matrix){

      for(element <- list){

         element match {

           case cell:KakuroSumCell =>
             print(cell.getSum + " ")

           case cell:KakuroInputCell =>
             print(cell.getRow + " " + cell.getColumn + ", ")

           case _ => print("null ")
         }

      }
      print("\n")

    }
  }




}