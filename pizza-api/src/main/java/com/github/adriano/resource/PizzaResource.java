package com.github.adriano.resource;

import java.util.Map;

import com.github.adriano.model.Category;
import com.github.adriano.model.Store;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pizzas")
@Transactional
public class PizzaResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getAll() {
        var store = Store.findNearest();
        var categories = Category.listByStore(store);
        return Map.of(
                "store", store,
                "categories", categories
        );
    }
}

