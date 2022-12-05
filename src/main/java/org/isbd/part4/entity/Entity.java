package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@javax.persistence.Entity
public class Entity {
    @GeneratedValue
    @Id
    private int id;
    @ManyToOne
    private Location location;

    private int health;
    private int name;
}
