import scala.io.Source
import java.io._
import scala.concurrent.Future
// master node가 worker node들에게 데이터를 일정 량만큼 전송한다.
// 이 때 일부는 남기고 전송한다(for sampling)
//master의 list에 대해 sort 함수 실행.

object Sampling {
    def initDataSending(filePath: String) {
        for (i <- 0 to 4) {
            // master에 있는 gensort 파일을 열어서 순차적으로 worker에게 보낸다.
            val line = Source.fromFile (filePath).getLines ()
            println (line)
            sendToWorker (line, i)
        }
    }

    def definePivot(sortedKeys: List[String]): List[List[Int]] = {
        val workerNum = 5
        val idxSize = sortedKeys.size / workerNum
        val newList = sortedKeys.grouped (idxSize).toList
        for (i <- 0 to 4) {
            sendToWorker(newList(i), i)
        }
    }
    def main() {
        val fut = Future {
            initDataSending("/pseudoGenSort.txt")
        }
        val result = fut.definePivot()
    }
}
