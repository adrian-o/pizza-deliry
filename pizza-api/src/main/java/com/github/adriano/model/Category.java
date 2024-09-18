package com.github.adriano.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Entity
public class Category extends PanacheEntity {
    public String name;

    @Column(precision = 10, scale = 2)
    public BigDecimal price;

    @JsonIgnore
    @ManyToOne
    public Store store;

    @ManyToMany(
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            })
    @JoinTable(
        name="pizza_category",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    public List<Pizza> pizzas;

    public Category() {}

    @Transactional
    public static Category persistCategory(Store store, String name, BigDecimal price) {
        var result = new Category();
        result.store = store;
        result.name = name;
        result.price = price;
        result.pizzas = new ArrayList<>();

        result.persist();

        return result;
    }

    public void addPizzas(Pizza... pzs) {
        this.pizzas.addAll(Arrays.asList(pzs));
    }

    public static List<Category> listByStore(Store store) {
        List<Category> res = list("store", Sort.by("price").ascending(), store);
        return res;
    }
}
