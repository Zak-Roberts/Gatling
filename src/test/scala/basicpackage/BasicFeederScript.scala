package test.scala.basicpackage

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.{Level, LoggerContext}

class BasicFeederScript extends Simulation{

//  val states = csv("/data/state.csv").circular
    val states = csv("C:\\Users\\Zak\\Documents\\CognizantLearning\\Gatling\\GatlingProjects\\warmupproject\\src\\test\\resources\\data\\states.csv").circular
  
  val httpConf = http.baseUrl("https://api.openbrewerydb.org")
  
  val scn = scenario("basicgetscenario").feed(states).exec(http("basicgetrequest").get("/breweries?by_state=${state}"))
  
  setUp(scn.inject(atOnceUsers(3))).protocols(httpConf)
  
}