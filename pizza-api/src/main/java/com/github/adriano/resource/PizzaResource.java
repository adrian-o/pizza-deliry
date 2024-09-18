package com.github.adriano.resource;

import java.util.Map;

import com.github.adriano.model.Pizza;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pizzas")
@Transactional
public class PizzaResource {

    @GET
    @Path("/{id}")
    public Pizza read(@PathParam(value="id") Long id) {
        return Pizza.findById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Pizza create(Map<String, Object> params) {
        String description = (String) params.get("description");
        return Pizza.persist(description);
    }
}

