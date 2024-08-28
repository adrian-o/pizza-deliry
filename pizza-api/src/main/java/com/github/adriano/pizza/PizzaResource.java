package com.github.adriano.pizza;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/pizzas")
public class PizzaResource {

    @Transactional
    public void init(@Observes StartupEvent evt) {
        var p1 = new Pizza();
        p1.description = "Marguerita";;
        p1.persist();

        var p2 = new Pizza();
        p2.description = "Calabresa";
        p2.persist();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pizza> getAll() {
        return Pizza.listAll();
    }
}

