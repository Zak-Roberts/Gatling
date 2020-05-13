package test.scala.injectionprofile

import io.gatling.http.Predef._
import io.gatling.core.Predef._

class BasicGet_OpenModel extends Simulation {
  
  val httpConf = http.baseUrl("http://newtours.demoaut.com")
  
  val scn = scenario("basicGetScenario").repeat(2){
    pace(2)
    .exec(http("basicGetRequest").get("/mercurycruise.php"))
  }
    setUp(scn.inject(rampUsers(3) during (5))).protocols(httpConf)
  
}