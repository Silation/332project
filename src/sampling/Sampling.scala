import Sampling.initDataSending

import scala.io.Source
import java.io._
import java.io.File
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import scala.io.Source.fromFile
import scala.util.control.Breaks

object Sampling {
    def initDataSending(filePath: String) {
        // 파일의 길이를 읽고 1/5만큼 반복하면서 리스트에 추가
        val openedFile = io.Source.fromFile(filePath)
        val fileSize = openedFile.getLines.size // 160,000,000 / 5 = 32,000,000
        val chunkSize = (fileSize / 5).asInstanceOf[Int]
        val chunk1 = new Array[String](chunkSize)
        val chunk2 = new Array[String](chunkSize)
        val chunk3 = new Array[String](chunkSize)
        val chunk4 = new Array[String](chunkSize)
        val chunk5 = new Array[String](chunkSize)
        val brk = new Breaks
        val totalLines = Source.fromFile(filePath).getLines()
        var i = 0
        for (line <- totalLines) {
            chunk1[i] = line
        }
//            sendToWorker (line, i)
    }

    def definePivot(sortedKeys: List[String]) = {
        val workerNum = 5
        val idxSize = sortedKeys.size / workerNum
        val newList = sortedKeys.grouped (idxSize).toList
//        for (i <- 0 to 4) {
////            sendToWorker(newList(i), i)
//        }
    }

}
object Main extends App{
    var test = Sampling initDataSending("C:\\cs434\\332project\\src\\smalltestfile")
    def main(): Unit = {
        println("start")
        initDataSending("../testfile")
        //        val result = definePivot()
    }
}
