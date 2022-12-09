package zio_grpc.examples.helloworld

import io.grpc.examples.helloworld.helloworld.ZioHelloworld.GreeterClient
import io.grpc.examples.helloworld.helloworld.HelloRequest
import io.grpc.ManagedChannelBuilder
import zio.Console._
import scalapb.zio_grpc.ZManagedChannel
import zio._
import scala.io.Source

import io.grpc.examples.helloworld.helloworld.{HelloReply, HelloRequest, sortReply, sortRequest}

import scala.io.Source.fromFile
import java.io._

// import scala.collection.JavaConversions._

object HelloWorldClient extends zio.ZIOAppDefault {
  val clientLayer: Layer[Throwable, GreeterClient] =
    GreeterClient.live(
      ZManagedChannel(
        ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext()
      )
    )

  def myAppLogic =
    for {
      // r <- GreeterClient.sayHello(HelloRequest("World"))
      // _ <- printLine(r.message)
      // r <- GreeterClient.sayHello(HelloRequest("bye bye World"))
      // _ <- printLine(r.message)
      
      //def flatVector(s: String): Option[String] = Try(s.replace("Vector(", ""), s.replace("Vector)", "")).toOption
      s <- GreeterClient.sayHelloAgain(HelloRequest( Seq("a"))) //, "World", "b", "G") ))
      // _ <- printLine(s.message.map(_.replace(")", "").replace("Vector(", "").toString).max)//.split(", ").map(_.toString).distinct.sorted.toString.toString )

    } yield ()

  final def run =
    myAppLogic.provideLayer(clientLayer).exitCode
}
