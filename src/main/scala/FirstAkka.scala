

import akka.actor.{ActorRef, ActorSystem, Props, Terminated}
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object FirstAkka extends App {


  val actorSystem = ActorSystem("FirstAkkaActorSystem")
  protected implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val timeout: Timeout = Timeout(5.second)

  println(actorSystem)

  private val arithmeticCalculatorActorRef: ActorRef = actorSystem.actorOf(Props[ArithmeticCalculatorActor] ,"ArithmeticCalculatorActor")
  println(arithmeticCalculatorActorRef.path)

  import ArithmeticCalculatorActor._

  arithmeticCalculatorActorRef ! Add(29)
  arithmeticCalculatorActorRef ! Add(29)
  arithmeticCalculatorActorRef ! Subtract(3)
  arithmeticCalculatorActorRef ! Multiply(30)
  arithmeticCalculatorActorRef ! Divide(10)
  arithmeticCalculatorActorRef ! Multiply(20)

  private val futureResult: Future[Int] = (arithmeticCalculatorActorRef ? Result).mapTo[Int]

  futureResult.onComplete{
    case Success(response) => println(s"Output of Operation ::::: $response")
    case Failure(exception)=> println("Something wrong has happened")
  }

  private val futureTerminated: Future[Terminated] = actorSystem.terminate()

  futureTerminated.onComplete{
    case Success(value )=> println("Successfully Terminated Actor System")
    case Failure(exception) => println("Error while terminating Actor System")
  }
}
