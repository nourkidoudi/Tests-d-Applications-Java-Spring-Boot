package com.example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

/**
 * SLOs (Service Level Objectives) :
 * 1. Temps de réponse maximum (95e percentile) : < 300ms pour GET /api/employes
 * 2. Taux d'erreur maximum : < 1%
 * 3. Charge cible : 30 utilisateurs simultanés sur 10 secondes
 */
public class EmployeSimulation extends Simulation {

    HttpProtocolBuilder http = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    ScenarioBuilder scenario = scenario("Consultation employés")
            // GET liste complète
            .exec(http("GET /api/employes")
                    .get("/api/employes")
                    .check(status().is(200)))
            .pause(1)
            // GET employé par ID (On suppose que l'ID 1 existe ou on peut ajuster)
            .exec(http("GET /api/employes/1")
                    .get("/api/employes/1")
                    .check(status().in(200, 404))); // 404 est acceptable si l'ID n'existe pas encore

    {
        setUp(
                scenario.injectOpen(
                        rampUsers(30).during(10)
                )
        ).protocols(http)
                .assertions(
                        global().responseTime().percentile(95).lt(300),
                        global().successfulRequests().percent().gt(99.0)
                );
    }
}
