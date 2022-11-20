import scala.io.Source.fromFile
import java.io._

val workerNum = 5


object LineSort (val lines: List[String]) List[String] = {
    def main(args: Array[String]) {
        val sorted_lines = lines.sorted
    }

    // pivot1 =< worker_keys < pivot2
    def setPivot(val keys: List[String]) List[String] = {
        idxSize = keys.size / workerNum

        val pivotKeys = List() ++ (          
            for {
                i <- 0 until workerNum
            }yield keys(idxSize * i)
        )
    }
}
