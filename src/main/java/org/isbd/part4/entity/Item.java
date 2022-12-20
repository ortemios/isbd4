package org.isbd.part4.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@javax.persistence.Entity
public class Item {
    @GeneratedValue
    @Id
    private int id;
    private String type;
    private int health;
    private int damage;
    private String name;

    public Item(int i, String type, int i1, int i2, String name) {
        this.id=i;
        this.type=type;
        this.health=i1;
        this.damage=i2;
        this.name=name;
    }

    public Item() {

    }
}
