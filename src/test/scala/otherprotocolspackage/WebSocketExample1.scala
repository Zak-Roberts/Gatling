package test.scala.otherprotocolspackage

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class WebSocketExample1 extends Simulation{
  
  // https://www.websocket.org/echo.html
  // wss://echo.websocket.org
  
  val httpProtocol = http.baseUrl("https://www.websocket.org/echo.html").wsBaseUrl("wss://echo.websocket.org")
  
  val scn = scenario("testwebsocketscenario")
  .exec(http("firsthttprequest").get("/"))
  .exec(ws("opensocket").connect("/").onConnected(exec(ws("sendmessage").sendText("Hello!")).pause(3).exec(ws("secondmessage").sendText("hello2!").await(20)(ws.checkTextMessage("check1").check(regex(".*hello.*").saveAs("mystring"))))))
  .exec(session=>
    {println("string output: " + session("mystring").as[String])
      session}
    )
  .exec(ws("closeconnection").close)
  
  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
  
}