package server

import io.grpc.ManagedChannelBuilder
import scala.io.Source
import io.grpc.{Server, ServerBuilder, Status}
import io.grpc.Status

import sortRPC.WorkertoWorker._

import scala.io.Source.fromFile
import java.io._
import java.util.logging.Logger

import scala.concurrent.{ExecutionContext, Future}

object workerServer{
  private val logger = Logger.getLogger(classOf[WorkerServer].getName)

  def main(args: Array[String]): Unit = {
    val server = new WorkerServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port: Int = 50051
}

class WorkerServer(executionContext: ExecutionContext) { self =>
  private[this] var server: Server = null

  private def start(): Unit = {
    server = ServerBuilder.forPort(WorkerServer.port).addService(WMSignalGrpc.bindService(new WorkerImpl, executionContext)).build.start
    WorkerServer.logger.info("Server started, listening on " + WorkerServer.port)
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  private def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  private def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }

  class WorkerImpl extends WMSignalGrpc.WMSignal {
    var a = -1
    override def workerConnection(request: sortRPC.WorkertoMaster.connectionSig): scala.concurrent.Future[sortRPC.WorkertoMaster.WorkerNum] = {
      a = a + 1
      val reply = sortRPC.WorkertoWorker.WorkerNum(number = a)
      Future.successful(reply)
    }

    /*: WorkerNum = {
      a = a + 1
      new WorkerNum(a)
    }*/

    override def sampling(request: SortFinishSig): Future[pivotVal] = {
      val reply = pivotVal(pivotOfWorker = "string key for each worker")
      Future.successful(reply)
    }

    // : pivotVal = new pivotVal("string key for each worker")

    override def mergeFinish(request: MergeFinishSig): Future[emptySig] = {
      val reply = emptySig()
      Future.successful(reply)
    }

    // : emptySig = new emptySig
  }

}