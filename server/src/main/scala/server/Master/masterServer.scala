package server

import io.grpc.ManagedChannelBuilder
import scala.io.Source
import io.grpc.{Server, ServerBuilder, Status}
import io.grpc.Status

import sortRPC.MastertoWorker._

import scala.io.Source.fromFile
import java.io._
import java.util.logging.Logger

import scala.concurrent.{ExecutionContext, Future}

object MasterServer{
  private val logger = Logger.getLogger(classOf[MasterServer].getName)

  def main(args: Array[String]): Unit = {
    val server = new MasterServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port: Int = 50051
}

class MasterServer(executionContext: ExecutionContext) { self =>
  private[this] var server: Server = null

  private def start(): Unit = {
    server = ServerBuilder.forPort(MasterServer.port).addService(MWSignalGrpc.bindService(new MasterImpl, executionContext)).build.start
    MasterServer.logger.info("Server started, listening on " + MasterServer.port)
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

  class MasterImpl extends MWSignalGrpc.MWSignal {
    var a = -1
    override def workerConnection(request: sortRPC.MastertoWorker.connectionSig): scala.concurrent.Future[sortRPC.MastertoWorker.WorkerNum] = {
      a = a + 1
      val reply = sortRPC.MastertoWorker.WorkerNum(number = a)
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