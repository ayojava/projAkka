
import akka.actor.{Actor, ActorLogging}

object ArithmeticCalculatorActor{

  

  case object Reset
  case object Result
  case object Added
  case object Subtracted
  case object Multiplied 
  case object Divided 

  case class Add(num : Int)
  case class Subtract(num : Int)
  case class Multiply(num : Int)
  case class Divide(num : Int)

  class Calculator{
   private var num:Int = 0

    def reset() = {
      num = 0 
      num 
    }

    def add(x: Int) = {
     num  = num + x  
 }

    def sub(x: Int) = {
      num  -= x
    }

    def mul(x: Int) = {
     num =  num * x 
   
    }

    def div(x: Int) = {
      num = num / x 
    }

    def result = num 
}
  
}

class ArithmeticCalculatorActor extends Actor with ActorLogging{

  import ArithmeticCalculatorActor._

  log.info("Called ArithmeticCalculator ")

  val cal = new Calculator()

  //behaviour
  override def receive: Receive = {
    case Reset =>
      log.info(s" Resetting  ${cal.result} to 0 " )
      sender() ! cal.reset()

    case request: Add =>
      log.info(s"Adding  ${request.num} to ${cal.result} " )
        cal.add(request.num)
        sender ! Added 

    case request: Subtract =>
      log.info(s"Subtracting  ${request.num} from   ${cal.result} " )
      cal.sub(request.num)
      sender ! Subtracted

    case request: Multiply =>
      log.info(s"Multiplying  ${request.num} with  ${cal.result} " )
      cal.mul(request.num)
      sender ! Multiplied 

    case request: Divide =>
      log.info(s"Dividing ${cal.result} by ${request.num}    " )
      cal.div(request.num)
      sender ! Divided 

    case Result =>
      log.info(s"Returning result of previous calculation ${cal.result}")
      sender ! cal.result

    case _ =>
      log.error(" Bro We don't know what you are talking about")
  }
}
