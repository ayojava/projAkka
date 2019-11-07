
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
      arithmeticCalculatorActorRef ! Add(20)
      expectMsg(Added)

      arithmeticCalculatorActorRef ! Add(11)
      expectMsg(Added)

      arithmeticCalculatorActorRef ! Subtract(10)
      expectMsg(Subtracted)

      arithmeticCalculatorActorRef ! Result
      expectMsg(21)
    }

    "return 250 after calling Add(10) , Multiply (30)  Subtract (50)" in {
      arithmeticCalculatorActorRef ! Reset
      expectMsg(0)

      arithmeticCalculatorActorRef ! Add(10)
      expectMsg(Added)

      arithmeticCalculatorActorRef ! Multiply(30)
      expectMsg(Multiplied)

      arithmeticCalculatorActorRef ! Subtract(50)
      expectMsg(Subtracted)

      arithmeticCalculatorActorRef ! Result

      val response: Int = expectMsgType[Int]
      assert(response == 250)

    }
  }
}
