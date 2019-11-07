import akka.actor.{Actor, ActorSystem, Props}

object ActorsIntro extends App {

  // Part 1 - Create an Actor System
  //Recommended to have one in an application , since its heavyweight
  val actorSystem = ActorSystem("ActorsIntro")
  println(actorSystem.name)

  // Part 2 - Create Actor
  // WordCount Actor
  class WordCountActor extends Actor{

    var totalWords = 0;

    //behaviour of the actor
    override def receive: Receive = {
      case message:String =>
        println(s"I have received a message : $message")
        totalWords += message.split("").length
      case other => println(s"I cannot understand this message")
    }
  }

  // Part 3 - Instantiate an Actor
  val wordCountActorRef = actorSystem.actorOf(Props[WordCountActor],"wordCounterActorRef")

  // Part 4 - Send an object to the Actor / Communicate
  wordCountActorRef ! "This is my first Akka project" // tell method

  // this will not work
  // new WordCountActor
}
