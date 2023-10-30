package com.example.gatlingdemo;

import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import java.time.Duration;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class BasicSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080");

    ScenarioBuilder scn = scenario("BasicSimulation")
        .repeat(5).on(
            exec(http("car").get("/car")
                .check(status().is(200)))
                .pause(1)
                .exec(http("carpart")
                    .get("/carpart")
                    .check(status().is(200)))
        );

    {
        setUp(
            scn.injectOpen(rampUsers(8).during(Duration.ofSeconds(10)))
        ).protocols(httpProtocol);
    }
}
