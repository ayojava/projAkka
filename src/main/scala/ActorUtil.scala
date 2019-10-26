
import akka.actor.{Actor, ActorLogging}

object ArithmeticCalculatorActor{
  case object Reset
  case class Add(num : Int)
  case class Subtract(num : Int)
  case class Multiply(num : Int)
  case class Divide(num : Int)
  case object Result
}

class ArithmeticCalculatorActor extends Actor with ActorLogging{

  import ArithmeticCalculatorActor._

  log.info("Called ArithmeticCalculator ")
  //internal state
  var num: Int = 0

  //behaviour
  override def receive: Receive = {
    case Reset =>
      log.info(s" Resetting  ${num} to 0 " )
      num = 0
      sender() ! num

    case request: Add =>
      log.info(s"Adding  ${request.num} to  ${num} " )
      num += request.num

    case request:Subtract =>
      log.info(s"Subtracting  ${request.num} from   ${num} " )
      num -= request.num

    case request: Multiply =>
      log.info(s"Multiplying  ${request.num} with  ${num} " )
      num *= request.num

    case request:Divide=>
      log.info(s"Dividing   ${num} by ${request.num}    " )
      num /= request.num

    case Result =>
      log.info(s"Returning result of previous calculation ${num}")
      sender() ! num

    case _ =>
      log.error(" Bro We don't know what you are talking about")
  }
}
