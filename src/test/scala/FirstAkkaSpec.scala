import ArithmeticCalculatorActor.Reset
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

class FirstAkkaSpec extends TestKit(ActorSystem("FirstAkkaSpec")) with ImplicitSender
  with WordSpecLike with BeforeAndAfterAll {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "FirstAkka " should {
    val arithmeticCalculatorActorRef: ActorRef = system.actorOf(Props[ArithmeticCalculatorActor])

    " return a 0 after calling reset " in {
        arithmeticCalculatorActorRef ! Reset

    }
  }
}
