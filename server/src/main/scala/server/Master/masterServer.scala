// sbt "runMain MasterServer"
package server

import io.grpc.ManagedChannelBuilder
import scala.io.Source
import io.grpc.{Server, ServerBuilder, Status}
import io.grpc.Status

import sortRPC.customSort._

import scala.io.Source.fromFile
import java.io._
import java.util.logging.Logger

import scala.concurrent.{ExecutionContext, Future}

object MasterServer{
  private val logger = Logger.getLogger(classOf[MasterServer].getName)
  private val port: Int = 50051
  
  def main(args: Array[String]): Unit = {
    val server = new MasterServer(ExecutionContext.global, port)
    server.start()
    server.blockUntilShutdown()
  }
}

class MasterServer(executionContext: ExecutionContext, port: Int) extends CommonServer(executionContext: ExecutionContext, port: Int) {}

abstract class CommonServer(executionContext: ExecutionContext, port: Int) { self =>
  private val logger = Logger.getLogger(classOf[CommonServer].getName)

  def start(): Unit = {
    server = ServerBuilder.forPort(port).addService(MWSignalGrpc.bindService(new MWSignalImpl, executionContext)).build.start
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
    override def workerConnection(req: sortRPC.customSort.connectionSig) = {
      a = a + 1
      val reply = sortRPC.customSort.WorkerNum(number = a)
      Future.successful(reply)
    }
    
    /*: WorkerNum = {
      a = a + 1
      new WorkerNum(a)
    }*/

    override def sampling(req: SortFinishSig): Future[pivotVal] = {
      val reply = pivotVal(pivotOfWorker = "string key for each worker")
      Future.successful(reply)
    }
      
    // : pivotVal = new pivotVal("string key for each worker")

    override def mergeFinish(req: MergeFinishSig): Future[emptySig] = {
      val reply = emptySig()
      Future.successful(reply)
    }
    
    // : emptySig = new emptySig
  }
}