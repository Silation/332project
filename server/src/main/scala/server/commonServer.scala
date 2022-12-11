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
import scala.collection.mutable.ArrayBuffer


abstract class CommonServer(executionContext: ExecutionContext, port: Int, TotalWorkerNumber: Int) { self =>


  private val logger = Logger.getLogger(classOf[CommonServer].getName)
  val server = ServerBuilder.forPort(port).addService(MWSignalGrpc.bindService(new MWSignalImpl, executionContext)).build.start

  def start(): Unit = {
    logger.info("Server started, listening on " + this.port)
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }

  private class MWSignalImpl extends MWSignalGrpc.MWSignal {
    var a = -1

    override def workerConnection(req: connectionSig) = {
      a = a + 1
      val workerIp = req.workerIP
      val workerPort = req.port
      val reply = WorkerNum(number = a)

      println("worker IP: " + workerIp)
      println("worker Port: " + workerPort)
      Future.successful(reply)
    }


    val accumulatedList = ArrayBuffer[String]()

    override def sampling(req: sampleValues): Future[pivotVal] = {
      accumulatedList ++= req.key.toList
      println("accumulated keys to worker" + req.workerNumber + ": " + accumulatedList)

      /* ---------------- 모든 worker로부터 key 받아올 때까지 기다림 ----------------*/

      val portion: Integer = (accumulatedList.length * req.workerNumber) /TotalWorkerNumber
      /*
      val reply = pivotVal
        portion match {
            case Ex1(b) if (b > 10) => println("Case 1")
            case Ex1(b) if (b == 5) => println("Case 2")
            case Ex2(3) => println("Case 3")
            case Ex2(b) => println("Case 4")
      }
      */
      println("key Index of worker: " + portion)
      println("List Length: " + accumulatedList.length)
      Future.successful(pivotVal(accumulatedList.sortWith(_ < _)(portion)) )//reply)
    }
      
    // : pivotVal = new pivotVal("string key for each worker")

    override def mergeFinish(req: MergeFinishSig): Future[emptySig] = {
      val reply = emptySig()
      val WorkerNum = req.workerNumber
      
      println(" worker" + WorkerNum +  "finished \n")
      Future.successful(reply)
    }
    
    // : emptySig = new emptySig
  }
}

object CommonServer {

}