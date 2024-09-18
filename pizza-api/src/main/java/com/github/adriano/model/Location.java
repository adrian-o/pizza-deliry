package com.github.adriano.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Location extends PanacheEntity {
    public static Location current() {
        return new Location();
    }
}
