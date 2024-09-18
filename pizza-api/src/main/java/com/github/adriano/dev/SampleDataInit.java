package com.github.adriano.dev;

import com.github.adriano.model.Category;
import com.github.adriano.model.Pizza;
import com.github.adriano.model.Store;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

public class SampleDataInit {

    @Inject
    LaunchMode mode;

    @Transactional
    public void init(@Observes StartupEvent evt) {
        if (LaunchMode.NORMAL.equals(mode))
            return;

        var store = Store.find("code", "__default__");

        if (store != null)
            return;

        var newStore = Store.persist("Pizza Shack", "__default__");

        var trad = Category.persistCategory(newStore,"Traditional", new BigDecimal("10.90"));

        var marg = Pizza.persist("Marguerita");
        var mush = Pizza.persist("Mushrooms");

        trad.addPizzas(marg, mush);

        var premium = Category.persistCategory(newStore,"Premium", new BigDecimal("15.90"));

        var cheeses = Pizza.persist("4 cheeses");
        var veegies = Pizza.persist("Vegetables");
        var napoles = Pizza.persist("Napoletana");

        premium.addPizzas(cheeses, veegies, napoles);

    }
}
