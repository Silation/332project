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

import java.util.concurrent.CountDownLatch


abstract class CommonServer(executionContext: ExecutionContext, port: Int, TotalWorkerNumber: Int) { self =>
  val latch1=new CountDownLatch(TotalWorkerNumber)
  val latch2=new CountDownLatch(TotalWorkerNumber)
  val finishLatch=new CountDownLatch(TotalWorkerNumber)

  private val logger = Logger.getLogger(classOf[CommonServer].getName)
  val server = ServerBuilder.forPort(port).addService(MWSignalGrpc.bindService(new MWSignalImpl, executionContext)).build.start

  def start(): Unit = {
    logger.info("Server started, listening on " + this.port)
    sys.addShutdownHook {
      self.stop()
    }
  }

  def stop(): Unit = {
    if (server != null) {
      server.shutdown()
      server.awaitTermination()
      System.err.println("*** server shut down")
    }
  }

  def blockUntilShutdown(): Unit = {
    if (server != null) {
      finishLatch.await()
      server.shutdown()
      server.awaitTermination()
      server.awaitTermination()
    }
  }

  private class MWSignalImpl extends MWSignalGrpc.MWSignal {
    var a = 0

    override def workerConnection(req: connectionSig) = {
      a = a + 1
      val workerIp = req.workerIP
      val workerPort = req.port
      val reply = WorkerNum(number = a)

      println("worker IP: " + workerIp)
      println("worker Port: " + workerPort)
      Future.successful(reply)
    }

     override def connectionFinish(req:emptySig)={
      latch1.countDown()
      latch1.await()
      val reply=emptySig()
      Future.successful(reply)
    }


    val accumulatedList = ArrayBuffer[String]()

    override def sampling(req: sampleValues): Future[pivotVal] = {
      accumulatedList ++= req.key.toList

      /* ---------------- 모든 worker로부터 key 받아올 때까지 기다림 ----------------*/
      println("\nSampling: Waiting for other connections...")
      latch2.countDown()
      latch2.await()
      println("success\n")

      val portion: Float = accumulatedList.length /TotalWorkerNumber

      val pivotList =
        for(i <- 1 to TotalWorkerNumber-1)
        yield accumulatedList.sortWith(_ < _)( (portion * i).toInt )
      Future.successful(pivotVal(pivotList))//reply)
    }
      
    // : pivotVal = new pivotVal("string key for each worker")

    override def mergeFinish(req: MergeFinishSig): Future[emptySig] = {
      val reply = emptySig()
      val WorkerNum = req.workerNumber
      finishLatch.countDown()
      println(" worker" + WorkerNum +  "finished \n")
      Future.successful(reply)
    }
    
    // : emptySig = new emptySig
  }
}

object CommonServer {

}