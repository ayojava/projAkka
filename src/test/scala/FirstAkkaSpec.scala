
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit._
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

class FirstAkkaSpec extends TestKit(ActorSystem("FirstAkkaSpec")) with ImplicitSender
  with WordSpecLike with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  import ArithmeticCalculatorActor._

  val probe = TestProbe()
  val arithmeticCalculatorActorRef: ActorRef = system.actorOf(Props[ArithmeticCalculatorActor], "cal")


  "FirstAkka " should {

    " return a 0 after calling reset " in {
        arithmeticCalculatorActorRef ! Reset
        expectMsg(0)
    }

    "return add and remove values  " in {
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
      arithmeticCalculatorActorRef ! Add(10)
      arithmeticCalculatorActorRef ! Multiply(30)
      arithmeticCalculatorActorRef ! Subtract(50)
      arithmeticCalculatorActorRef ! Result

      val response: Int = expectMsgType[Int]
      assert(response == 250)

    }
  }
}
