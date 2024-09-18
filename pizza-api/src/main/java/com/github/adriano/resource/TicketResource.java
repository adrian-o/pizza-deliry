package com.github.adriano.resource;

import com.github.adriano.model.Ticket;
import com.github.adriano.model.TicketItem;
import com.github.adriano.model.enums.TicketStatus;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/tickets")
public class TicketResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Ticket create(Map<String, Object> params) {
        Long clientId = ((Number) params.get("clientId")).longValue();
        String addressMain = (String) params.get("addressMain");
        String addressDetail = (String) params.get("addressDetail");
        String phone = (String) params.get("phone");

        return Ticket.persist(clientId, addressMain, addressDetail, phone, TicketStatus.CREATED);
    }

    @GET
    @Path("/{id}")
    public Ticket read(@PathParam(value = "id") Long id) {
        return Ticket.findById(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Ticket delete(@PathParam(value = "id") Long id) {
        Ticket ticket = read(id);
        ticket.status = TicketStatus.CANCELED;

        ticket.persistAndFlush();

        return ticket;
    }

    public Ticket addItem(Long id, TicketItem item) {
        return null;
    }

    public Ticket submit(Long id) {
        return null;
    }
}
