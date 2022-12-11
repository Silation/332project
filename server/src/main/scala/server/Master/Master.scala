// sbt "runMain MasterServer"
package master.client

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}

import scala.io.Source
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
    if (args.length > 2) {
      // worker 호출
      val (workerVariable, masterHost, masterPort, inputDirs, outputDir, bindingPort) = parse(args)
      val server = new MasterClient(ExecutionContext.global, bindingPort)
      server.start()
      server.blockUntilShutdown()
    }
    else {
      // master 호출
      val (masterVariable, workerNum) = parseArgs(args)
      val workerNum = args(1)
      val server = new MasterServer(ExecutionContext.global, port)
      server.start()
      server.blockUntilShutdown()
    }
  }
}