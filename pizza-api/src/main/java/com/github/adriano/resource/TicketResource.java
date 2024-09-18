package com.github.adriano.resource;

import com.github.adriano.model.Pizza;
import com.github.adriano.model.Ticket;
import com.github.adriano.model.TicketItem;
import com.github.adriano.model.enums.TicketStatus;
import com.github.adriano.resource.records.TicketItemAdd;

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

        return Ticket.persist(clientId, addressMain, addressDetail, phone);
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
        
        if (!TicketStatus.OPEN.equals(ticket.status)) 
            throw new BadRequestException("Ticket not Open");
        
        ticket.status = TicketStatus.CANCELED;

        ticket.persistAndFlush();

        return ticket;
    }

    @PUT
    @Path("/{id}/pizzas")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public TicketItem addItem(Long id, TicketItemAdd item) {
        Ticket ticket = read(id);
        Pizza pizza = Pizza.findById(item.pizzaId().intValue());

        if (ticket == null)
            throw new NotFoundException("Ticket Not Found");
        
        if (!TicketStatus.OPEN.equals(ticket.status)) 
            throw new BadRequestException("Ticket not Open");       
        
        if (item.quantity().intValue() < 0 ||
                item.quantity().intValue() > 20)
            throw new BadRequestException("You must request a acceptable pizzas quantities in this chanel [1-20] ");
        
        var ticketItem = TicketItem.persist(ticket, pizza, item.quantity().intValue(), item.price());

        return ticketItem;
    }

    @POST
    @Path("/{id}/submit")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Ticket submit(@PathParam(value = "id") Long id) {
        Ticket ticket = read(id);

        if (!TicketStatus.OPEN.equals(ticket.status)) 
            throw new BadRequestException("Ticket not Open");
        
        ticket.status = TicketStatus.SUBMITTED;

        ticket.persistAndFlush();

        return ticket;
    }
}
