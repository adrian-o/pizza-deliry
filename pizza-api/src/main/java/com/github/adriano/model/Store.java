package com.github.adriano.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.transaction.Transactional;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Store extends PanacheEntity {
    public String code;
    public String name;

    public Store() {}

    @Transactional
    public static Store persist(String name, String code) {
        var result = new Store();
        result.name = name;
        result.code = code;

        result.persist();

        return result;
    }

    public static Store findNearest() {
        return findNearest(Location.current());
    }

    public static Store findNearest(Location location) {
        Store store = Store.find("code", "__default__").firstResult();
        return store;
    }
}
