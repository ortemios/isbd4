package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@javax.persistence.Entity
public class Race {
    @GeneratedValue
    @Id
    private int id;
    @ManyToOne
    private Side side;
    private String name;
    private int health;
    private int damage;
}
