// sbt "runMain MasterServer"
package master

import io.grpc.ManagedChannelBuilder
import zio.Console._
import zio._
import scala.io.Source
import scalapb.zio_grpc.{ServerMain, ServiceList, ZManagedChannel}

import zio.Duration._
import zio.stream.{Stream, ZStream}
import io.grpc.{Server, ServerBuilder}
import io.grpc.Status
import zio.stream.ZSink
// import scalapb.zio_grpc.Server
import scalapb.zio_grpc.ServerLayer
import zio.Console.{print, printLine}

import sortRPC.customSort.{emptySig, connectionSig, SortFinishSig, pivotVal, MergeFinishSig, WorkerNum}

import scala.io.Source.fromFile
import java.io._

import java.util.logging.Logger

import scala.concurrent.{ExecutionContext, Future}



object MasterImpl {
  var a = -1
  def WorkerConnection(request: connectionSig): WorkerNum = {
    a = a + 1
    new WorkerNum(a)
  }
    // ZIO[R with Context, Status, emptySig]
    // ZIO.succeed(emptySig())


  def Sampling(request: SortFinishSig): pivotVal = new pivotVal("string key for each worker")
    // ZIO[R with Context, Status, pivotVal]
    // ZIO.succeed(pivotVal( "string key for each worker" ))

  def MergeFinish(request: MergeFinishSig): emptySig = new emptySig
    // ZIO[R with Context, Status, emptySig]
    // ZIO.succeed( emptySig( ) )
}

object MasterServer extends ServerMain {
  private val logger = Logger.getLogger(classOf[MasterServer].getName)
  
  def main(args: Array[String]): Unit = {
    val server = new MasterServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port: Int = 50051

  /*
  def services: ServiceList[Any] = ServiceList.add(MasterImpl)

  def serverWait: ZIO[Any, Throwable, Unit] =
    for {
      _ <- printLine("Server is running. Press Ctrl-C to stop.")
      _ <- (print(".") *> ZIO.sleep(1.second)).forever
    } yield ()

  def run = myAppLogic.exitCode

  val myAppLogic =
    serverWait.provideLayer(serverLive(9090))
    */

  /*
  val featuresDatabase = JsonFormat.fromJsonString[FeatureDatabase](
    Source.fromResource("route_guide_db.json").mkString
  )

  val createRouteGuide = for {
    routeNotes <- Ref.make(Map.empty[Point, List[RouteNote]])
  } yield new RouteGuideService(featuresDatabase.feature, routeNotes)

  def services: ServiceList[Any] =
    ServiceList.addZIO(createRouteGuide)
  */
}

class MasterServer(executionContext: ExecutionContext) { self =>
  private[this] var server: Server = null

  private def start(): Unit = {
    server = ServerBuilder.forPort(MasterServer.port).addService(GreeterGrpc.bindService(new MasterImpl, executionContext)).build.start
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

}