
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

class FirstAkkaSpec extends TestKit(ActorSystem("FirstAkkaSpec")) with ImplicitSender
  with WordSpecLike with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  import ArithmeticCalculatorActor._

  "FirstAkka " should {
    val arithmeticCalculatorActorRef: ActorRef = system.actorOf(Props[ArithmeticCalculatorActor])

    " return a 0 after calling reset " in {
        arithmeticCalculatorActorRef ! Reset
        expectMsg(0)
    }

    "return 15 after calling Add(25) Subtract (10)" in {
      arithmeticCalculatorActorRef ! Add(25)
      arithmeticCalculatorActorRef ! Subtract(10)
      arithmeticCalculatorActorRef ! Result
      expectMsg(15)
    }

    "return 250 after calling Add(10) , Multiply (30)  Subtract (50)" in {
      arithmeticCalculatorActorRef ! Reset
      arithmeticCalculatorActorRef ! Add(10)
      arithmeticCalculatorActorRef ! Multiply(30)
      arithmeticCalculatorActorRef ! Subtract(50)
      arithmeticCalculatorActorRef ! Result

      val response: Int = expectMsgType[Int]
      assert(response == 250)

    }
  }
}
