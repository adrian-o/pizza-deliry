package com.github.adriano.resource;

import com.github.adriano.model.Client;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/clients")
public class ClientResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Client create(Map<String, Object> params) {
        String name = (String) params.get("name");
        String email = (String) params.get("email");
        String phone = (String) params.get("phone");

        return Client.persist(name, email, phone);
    }

    @GET
    @Path("/{id}")
    public Client read(@PathParam(value = "id") Long id) {
        return Client.findById(id);
    }
}
