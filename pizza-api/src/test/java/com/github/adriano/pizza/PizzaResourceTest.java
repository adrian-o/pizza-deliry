package com.github.adriano.pizza;

import com.github.adriano.model.*;
import com.github.adriano.model.enums.TicketStatus;
import com.github.adriano.resource.PizzaResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

@QuarkusTest
public class PizzaResourceTest {

    @Inject
    PizzaResource pizzaResource;

    @BeforeAll
    @Transactional
    public static void beforeAll() {
        var store = new Store();
        store.name = "Pizza Shack";
        store.code = "__default__";

        store.persist();
    }
    /**
     * Initial pizza order happy flow
     * 1. Show menu of the nearest store
     * 2. Add pizza to cart
     * 3. Review cart before checkout
     * 4. Checkout
     * 5. Delivery
     * 6. Feedback
     */

    @Test
    public void testFindNearestStore() {
        // Given
        var location = Location.current();

        // When
        var store = Store.findNearest(location);

        // Then
        Assertions.assertNotNull(store);
    }

    @Test
    public void testAddToTicket() {

        // GIVEN
        var store = Store.persist("Test Shack", "__test__");
        var trad = Category.persistCategory(store,"Traditional", new BigDecimal("10.90"));
        var marg = Pizza.persist("Marguerita");
        var mush = Pizza.persist("Mushrooms");
        trad.addPizzas(marg, mush);

        // WHEN
        var person = Client.persist("Adriano", "adriano.oh@email.com", "1198099098");
        var ticket = Ticket.persist(person.id, "Av Vila Ola, 488", "Alameda 4", "11999999999");
        var finalPrice = ticket.showTotal();

        // THEN
        Assertions.assertEquals(BigDecimal.valueOf(32.70).setScale(2, RoundingMode.HALF_UP),
                finalPrice.setScale(2, RoundingMode.HALF_UP));
    }

}