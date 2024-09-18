package com.github.adriano.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.adriano.model.enums.TicketStatus;

@Entity
public class Ticket extends PanacheEntity {

    public LocalDateTime startedAt;

    @ManyToOne
    public Client client;

    public String phone;

    public String addressMain;

    public String addressDetail;

    @Enumerated(EnumType.STRING)
    public TicketStatus status;

    @OneToMany(mappedBy = "ticket", 
                cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
                fetch = FetchType.LAZY)
    @JsonIgnore
    public List<TicketItem> items = new ArrayList<>();

    public Ticket() {}

    @Transactional
    public static Ticket persist(Long clientId, String addressMain, String addressDetail, String phone, TicketStatus status) {
        Client client = Client.findById(clientId);
        return persist(client, addressMain, addressDetail, phone, status);
    }

    @Transactional
    public static Ticket persist(Client client, String addressMain, String addressDetail, String phone, TicketStatus status) {
        var result = new Ticket();
        result.client = client;
        result.addressMain = addressMain;
        result.addressDetail = addressDetail;
        result.phone = phone;
        result.startedAt = LocalDateTime.now();
        result.status = status;

        result.persist();

        return result;
    }

    public void addItem(Pizza pizza, Integer quantity, BigDecimal price) {
        TicketItem item = TicketItem.persist(this, pizza, quantity, price);
        items.add(item);
    }

    @Transient
    public BigDecimal showTotal() {
        var result = items.stream()
                .map(TicketItem::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return result;
    }
}
