package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Developer extends PanacheEntity {

    public static Developer of(String name){
        return new Developer(name);
    }

    private String name;

    public Developer() {
        this(null);
    }

    public Developer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
