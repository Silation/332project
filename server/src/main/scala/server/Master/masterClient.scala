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


object MasterClient {
  def apply(host: String, port: Int): MasterClient = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = MWSignalGrpc.blockingStub(channel)
    new MasterClient(channel, blockingStub)
  }


  val localhost: InetAddress = InetAddress.getLocalHost
  val localIpAddress: String = localhost.getHostAddress
  val clientPort: Integer = 80 //InetSocketAddress(localhost).getPort //.getLocalHost.getPort
    //30 //InetSocketAddress.getPort()


  def main(args: Array[String]): Unit = {
    /*------------workerConnection function--------------*/
    val client = MasterClient("localhost", 50051)
    var workerNum = 0
    
    try {
      workerNum = client.workerConnection(ip=localIpAddress, port=clientPort)
    } finally {
      // client.shutdown()
    }


    /*------------sampling function--------------*/
    val temp = List("ac", "a", "b", "c", "d", "e", "f")
    try {
      val pivotString = client.sampling(temp, workerNum)
    } finally {
      // client.shutdown()
    }


    /*------------mergeFinish function--------------*/
    try {
      client.mergeFinish(workerNum)
    } finally {
      client.shutdown()
    }
  }
}

class MasterClient private(
  private val channel: ManagedChannel,
  private val blockingStub: MWSignalBlockingStub
) {
  private[this] val logger = Logger.getLogger(classOf[MasterClient].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }
  

  /* -------------- rpc functions ------------------ */
  def workerConnection(ip: String, port: Integer): Integer = {
//    logger.info("connecting from " + "" + bindingPort.get)

    val request = connectionSig(workerIP=ip, port=port)
    try {
      val response = blockingStub.workerConnection(request)
      logger.info("Number of this worker: " + response.number.toString)
      response.number
    }
    catch {
      case e: StatusRuntimeException =>
        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus)
        -1
    }
  }
  
  def sampling(keys: List[String], workerNum: Int): String = {
    logger.info("Sending samples...")

    val request = sampleValues(keys, workerNum)
    try {
      val response = blockingStub.sampling(request)
      logger.info("pivot of this worker: " + response.pivotOfWorker)
      response.pivotOfWorker
    }
    catch {
      case e: StatusRuntimeException =>
        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus)
        "error"
    }
  }
  
  def mergeFinish(workerNum: Int): Unit = {
    logger.info("Sending finish signal ...")

    val request = MergeFinishSig(workerNumber=workerNum)
    try {
      val response = blockingStub.mergeFinish(request)
      logger.info("Finish of this worker: " + workerNum.toString)
    }
    catch {
      case e: StatusRuntimeException =>
        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus)
    }
  }
}