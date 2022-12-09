package zio_grpc.examples.helloworld

import io.grpc.Status
import scalapb.zio_grpc.ServerMain
import scalapb.zio_grpc.ServiceList
import zio._
import zio.Console._

import io.grpc.examples.helloworld.helloworld.ZioHelloworld.ZGreeter
import io.grpc.examples.helloworld.helloworld.{HelloReply, HelloRequest, sortReply, sortRequest}

import scala.io.Source.fromFile
import java.io._

object GreeterImpl extends ZGreeter[Any, Any] {
  /*
  def sayHello(request: HelloRequest): ZIO[Any, Status, HelloReply] =
    printLine(s"Got request: $request").orDie zipRight
      ZIO.succeed(HelloReply(s"Hello hi hi, ${request.name}".sorted))
  */

  def sayHelloAgain(request: HelloRequest) =
    ZIO.succeed(HelloReply( Seq(s"${request.name}") ))
    // ZIO.succeed(HelloReply(s"Hello again, ${request.name.sorted}"))
  
  /*
  def printSort(request: HelloRequest) =
    sorted = request.name.sorted
    ZIO.succeed(HelloReply(s"Hello again, ${request.name}"))
    */
}

object HelloWorldServer extends ServerMain {
  def services: ServiceList[Any] = ServiceList.add(GreeterImpl)
}
