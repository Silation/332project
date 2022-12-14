// sbt "runMain MasterServer"
package worker.client

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}

import io.grpc.{ManagedChannelBuilder, Status, StatusRuntimeException, ManagedChannel, Channel}

import sortRPC.customSort._

import scala.io.Source.fromFile
import java.io._
import java.util.logging.Logger

import scala.concurrent.{ExecutionContext, Future}

import MWSignalGrpc.MWSignalBlockingStub

import serverConnection._

import java.net._

import helper.Parser._



object entryPoint {

  def main(args: Array[String]): Unit = {
    val TotalWorkerNumber = 0
    if (args.length > 2) {
      // worker 호출
      val (masterIP, masterPort, inputDirsPath, outputDirPath, workerIP) = parse(args)
      println(masterIP, masterPort, inputDirsPath(0), outputDirPath, workerIP)
      val worker = new WorkerClient(masterIP, 50052, inputDirsPath(0), outputDirPath)
      worker.start()
    }
    else {
      // master 호출
      val TotalWorkerNumber = args(1).toInt
      val server = new MasterServer(ExecutionContext.global, 50052, TotalWorkerNumber)
      server.start()
      server.blockUntilShutdown()
    }
  }
}
