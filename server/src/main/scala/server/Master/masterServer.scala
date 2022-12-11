// sbt "runMain MasterServer"
package serverConnection

import io.grpc.ManagedChannelBuilder
import scala.io.Source
import io.grpc.{Server, ServerBuilder, Status}
import io.grpc.Status

import sortRPC.customSort._

import scala.io.Source.fromFile
import java.io._
import java.util.logging.Logger

import scala.concurrent.{ExecutionContext, Future}


import serverConnection._


object MasterServer{
  private val logger = Logger.getLogger(classOf[MasterServer].getName)
  private val port: Int = 50051
  
  def main(args: Array[String]): Unit = {
    val server = new MasterServer(ExecutionContext.global, port, TotalWorkerNumber)
    server.start()
    server.blockUntilShutdown()
  }
}

class MasterServer(executionContext: ExecutionContext, port: Int, TotalWorkerNumber: Int) extends serverConnection.CommonServer(executionContext: ExecutionContext, port: Int, TotalWorkerNumber: Int)
