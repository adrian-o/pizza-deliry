package com.github.adriano.pizza;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import com.github.adriano.model.Ticket;
import com.github.adriano.model.enums.TicketStatus;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Map;

@QuarkusTest
public class TicketResourceTest {

    @Inject
    Ticket ticket;
    
    @Test
    public void testAddToTicket() {
        Log.infof("Test created ticket %s", ticket.id);
        
        given()
                .body(Map.of(
                    "clientId", 1,
                    "addressMain", "Av Vila Ola, 488",
                    "addressDetail", "Alameda 4",
                    "addressPhone", "11999999999" 
                ))
                .contentType("application/json")
            .when()
                .post("/tickets")
            .then()
                .statusCode(200)
                .body("status", equalTo(TicketStatus.OPEN.toString()));
    }

    @Test
    public void testDeleteTicket() {
        Log.infof("Test delete ticket %s", ticket.status);
        
        given()
                .pathParam("id", 1)
                .contentType("application/json")
            .when()
                .delete("/tickets/{id}")
            .then()
                .statusCode(200)
                .body("status", equalTo(TicketStatus.CANCELED.toString()));
    }
}