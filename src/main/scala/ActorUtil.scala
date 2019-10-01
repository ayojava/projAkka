
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
  var num: Int = 0;

  //behaviour
  override def receive: Receive = {
    case Reset =>
      log.info(s" Resetting  num {$num} to 0 " )
      num = 0;

    case Add(add) =>
      log.info(s"Adding  {$add} to  num {$num} " )
      num += add

    case Subtract(subtract) =>
      log.info(s"Subtracting  {$subtract} from  num {$num} " )
      num -= subtract

    case Multiply(multiply) =>
      log.info(s"Multiplying  {$multiply} to  num {$num} " )
      num *= multiply

    case Divide(divide) =>
      log.info(s"Dividing  num {$num} by {$divide}    " )
      num /= divide

    case Result =>
      log.info(s"Returning result of previous calculation")
      sender() ! num

    case _ =>
      log.error(" Bro We don't know what you are talking about")
  }
}
