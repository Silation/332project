package helper
import java.io.File

import java.net.InetAddress

object Parser {
  def parse(args: Array[String]): (String, Int, Array[String], String, String) = {

    val masterIPPort = args(1)
    val inputDirPaths = args.slice(3, args.length - 2)
    val outputDirPath = args(args.length - 1)

    val masterIP = masterIPPort.split(":")(0)
    val masterPort = masterIPPort.split(":")(1).toInt
    (masterIP, masterPort, inputDirPaths, outputDirPath, InetAddress.getLocalHost.getHostAddress)
  }
}