// sbt "runMain MasterServer"
package master

import io.grpc.ManagedChannelBuilder
import zio.Console._
import zio._
import scala.io.Source
import scalapb.zio_grpc.{ServerMain, ServiceList, ZManagedChannel}

import zio.Duration._
import zio.stream.{Stream, ZStream}
import io.grpc.ServerBuilder
import io.grpc.Status
import zio.stream.ZSink
import scalapb.zio_grpc.Server
import scalapb.zio_grpc.ServerLayer
import zio.Console.{print, printLine}

import sortRPC.customSort.{emptySig, connectionSig, SortFinishSig, pivotVal, MergeFinishSig}

import scala.io.Source.fromFile
import java.io._


trait ZMasterImpl[R, Context] {
  def WorkerConnection(request: connectionSig):
    ZIO[R with Context, Status, emptySig]
    // ZIO.succeed( emptySig( ) )


  def Sampling(request: SortFinishSig) =:
    ZIO[R with Context, Status, pivotVal( "string key for each worker" )]
    //ZIO.succeed(pivotVal( "string key for each worker" ))

  def MergeFinish(request: MergeFinishSig):
    ZIO[R with Context, Status, emptySig]
    // ZIO.succeed( emptySig( ) )
}

trait ServiceNameImpl extends ServiceName {}


object MasterServer extends ServerMain {
  override def port: Int = 8980

  def services: ServiceList[Any] = ServiceList.add(MasterImpl)

  def serverWait: ZIO[Any, Throwable, Unit] =
    for {
      _ <- printLine("Server is running. Press Ctrl-C to stop.")
      _ <- (print(".") *> ZIO.sleep(1.second)).forever
    } yield ()

  def serverLive(port: Int): Layer[Throwable, Server] =
    Clock.live >>> GreeterService.live >>> ServerLayer.access[Greeter](
      ServerBuilder.forPort(port)
    )

  def run = myAppLogic.exitCode

  val myAppLogic =
    serverWait.provideLayer(serverLive(9090))

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