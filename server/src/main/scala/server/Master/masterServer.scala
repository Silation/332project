// sbt "runMain MasterServer"
package serverConnection

import io.grpc.ManagedChannelBuilder
import scala.io.Source
import io.grpc.{Server, ServerBuilder, Status}
import io.grpc.Status

import sortRPC.customSort._


import scala.concurrent.{ExecutionContext, Future}


import serverConnection._


class MasterServer(executionContext: ExecutionContext, port: Int, TotalWorkerNumber: Int) extends serverConnection.CommonServer(executionContext: ExecutionContext, port: Int, TotalWorkerNumber: Int)
