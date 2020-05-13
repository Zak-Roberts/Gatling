package test.scala.basicpackage

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.{Level, LoggerContext}

class BasicGetScript_session extends Simulation{
      
  val httpConf = http.baseUrl("https://api.openbrewerydb.org")
  
  val scn = scenario("basicgetscenario").exec(http("basicgetrequest").get("/breweries")
      .check(bodyString.saveAs("myResponse")))
      .exec{session =>
        val response1 = session("myResponse").as[String]      
        println(response1)
        session
    }
  
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
  
}