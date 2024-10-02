package com.github.adriano.pizza.data;

import java.math.BigDecimal;

import com.github.adriano.model.Category;
import com.github.adriano.model.Client;
import com.github.adriano.model.Pizza;
import com.github.adriano.model.Store;
import com.github.adriano.model.Ticket;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class TestDataProducer {

    public Ticket ticket;

    public void init(@Observes StartupEvent evt) {
        Store store = Store.persist("Test Shack", "__test__");
        Category trad = Category.persistCategory(store, "Traditional", new BigDecimal("10.90"));
        Pizza marg = Pizza.persist("Marguerita");
        Pizza mush = Pizza.persist("Mushrooms");
        trad.addPizzas(marg, mush);

        // WHEN
        Client cli = Client.persist("Adriano", "adriano.oh@email.com", "1198099098");
        ticket = Ticket.persist(cli.id, "Av Vila Ola, 488", "Alameda 4", "11999999999");
    }

    @Produces
    public Ticket createTicket() {
        return this.ticket;
    }
}
