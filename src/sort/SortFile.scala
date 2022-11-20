import scala.io.Source.fromFile
import java.io._

object FileSort {
    val workerNum = 5
    
    def main(args: Array[String]) {
        val empPath = "pennyinput"
        val gensortLines = List() ++ (          
            for{                                
                line <- fromFile(empPath).getLines  
            }yield line                 
        ) 
        
        val sorted_lines = gensortLines.sorted

        val filename = "sorted_" + empPath
        val file = new File(filename)
        val bw = new BufferedWriter(new FileWriter(file))
        for (line <- sorted_lines) { bw.write(line) }
        bw.close()

        println(setPivot(sorted_lines))
    }

    def setPivot(keys: List[String]): List[String] = {
        val idxSize = keys.size / workerNum
        
        val pivotKeys = List() ++ (          
            for {
                i <- 0 until workerNum
            }yield keys(idxSize * i)
        )
        pivotKeys
    }

}
