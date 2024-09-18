package com.github.adriano.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;

@Entity
public class Client extends PanacheEntity {

    public String name;

    public String email;

    public String phone;

    public Client() {}

    @Transactional
    public static Client persist(String name, String email, String phone) {
        var result = new Client();
        result.name = name;
        result.email = email;
        result.phone = phone;

        result.persist();

        return result;
    }
}
