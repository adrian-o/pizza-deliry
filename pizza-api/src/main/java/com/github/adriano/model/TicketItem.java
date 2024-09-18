package com.github.adriano.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TicketItem extends PanacheEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public Ticket ticket;

    @ManyToOne
    public Pizza pizza;

    public Integer quantity;

    public BigDecimal price;

    public TicketItem() {}

    @Transactional
    public static TicketItem persist(Ticket ticket, Pizza pizza, Integer quantity, BigDecimal price) {
        var result = new TicketItem();
        result.ticket = ticket;
        result.pizza = pizza;
        result.quantity = quantity;
        result.price = price;

        result.persist();

        return result;
    }

    @Transient
    public BigDecimal getValue() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
