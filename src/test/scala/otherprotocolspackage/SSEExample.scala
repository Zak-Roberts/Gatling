package test.scala.otherprotocolspackage

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class SSEExample extends Simulation {

  // http://express-eventsource.herokuapp.com/events
  
  val myCheck1 = sse.checkMessage("checkId").check(jsonPath("$.id").find.saveAs("id"))
  val myCheck2 = sse.checkMessage("checkData").check(jsonPath("$.data").find.saveAs("data"))

  val httpProtocol = http.baseUrl("http://express-eventsource.herokuapp.com")

  val scn = scenario("sse_scenario")
    .exec(sse("sse_req").connect("/events").await(20)(sse.checkMessage("check_connect").check(regex(".*.*").exists)))
    .pause(2)
    .exec(sse("setcheck").setCheck.await(20)(myCheck1,myCheck2))
        .exec(session => {
      println(session("id").as[String])
      println(session("data").as[String])
      session
    })
    .exec(sse("sse_close").close())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

}